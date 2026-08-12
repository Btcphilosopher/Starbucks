package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.StarbucksAssistService
import com.example.data.StarbucksData
import com.example.data.StarbucksRepository
import com.example.data.models.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class MainTab {
    HOME,
    ORDER,
    REWARDS,
    STORES,
    MORE
}

data class AiChatMessage(
    val sender: String, // "user" or "assistant"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

class StarbucksViewModel(application: Application) : AndroidViewModel(application) {

    val repository = StarbucksRepository(application)
    private val assistService = StarbucksAssistService()

    // Navigation & UI state
    private val _currentTab = MutableStateFlow(MainTab.HOME)
    val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

    // Drink Customizer State
    private val _customizingDrink = MutableStateFlow<DrinkItem?>(null)
    val customizingDrink: StateFlow<DrinkItem?> = _customizingDrink.asStateFlow()

    private val _currentCustomization = MutableStateFlow(CustomizationOptions())
    val currentCustomization: StateFlow<CustomizationOptions> = _currentCustomization.asStateFlow()

    // Selected category filter on Order tab
    private val _selectedCategory = MutableStateFlow(DrinkCategory.COLD_COFFEE)
    val selectedCategory: StateFlow<DrinkCategory> = _selectedCategory.asStateFlow()

    // Active order / Checkout flow modal
    private val _showCheckoutSheet = MutableStateFlow(false)
    val showCheckoutSheet: StateFlow<Boolean> = _showCheckoutSheet.asStateFlow()

    private val _selectedFulfillment = MutableStateFlow(OrderFulfillmentType.PICKUP)
    val selectedFulfillment: StateFlow<OrderFulfillmentType> = _selectedFulfillment.asStateFlow()

    // Drive Thru Flow Modal
    private val _showDriveThruCode = MutableStateFlow(false)
    val showDriveThruCode: StateFlow<Boolean> = _showDriveThruCode.asStateFlow()

    // Card Reload Modal
    private val _showReloadSheet = MutableStateFlow(false)
    val showReloadSheet: StateFlow<Boolean> = _showReloadSheet.asStateFlow()

    // AI Assist Modal / Chat state
    private val _showAiAssistant = MutableStateFlow(false)
    val showAiAssistant: StateFlow<Boolean> = _showAiAssistant.asStateFlow()

    private val _aiMessages = MutableStateFlow(
        listOf(
            AiChatMessage("assistant", "Hello Tom! I'm Starbucks Assist ☕. Ask me for recommendations, low-calorie options, or your rewards status!")
        )
    )
    val aiMessages: StateFlow<List<AiChatMessage>> = _aiMessages.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    // Flow bindings
    val cartItems = repository.cartItems
    val starsCount = repository.starsCount
    val selectedStore = repository.selectedStore
    val activeOrder = repository.activeOrder
    val starbucksCard = repository.starbucksCardFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        StarbucksCardDetails()
    )
    val favoriteDrinks = repository.favoriteDrinksFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    val offers = repository.offers
    val notifications = repository.notificationsFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    val orderHistory = repository.orderHistoryFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun selectTab(tab: MainTab) {
        _currentTab.value = tab
    }

    fun selectCategory(category: DrinkCategory) {
        _selectedCategory.value = category
    }

    fun startCustomizingDrink(drink: DrinkItem) {
        _customizingDrink.value = drink
        _currentCustomization.value = drink.defaultOptions
    }

    fun closeCustomizer() {
        _customizingDrink.value = null
    }

    fun updateCustomization(options: CustomizationOptions) {
        _currentCustomization.value = options
    }

    fun addCustomizedDrinkToCart() {
        val drink = _customizingDrink.value ?: return
        repository.addToCart(drink, _currentCustomization.value)
        _customizingDrink.value = null
    }

    fun orderUsual() {
        val usualDrink = StarbucksData.sampleDrinks.first()
        repository.addToCart(usualDrink, usualDrink.defaultOptions)
        _showCheckoutSheet.value = true
    }

    fun setFulfillmentType(type: OrderFulfillmentType) {
        _selectedFulfillment.value = type
    }

    fun openCheckout() {
        _showCheckoutSheet.value = true
    }

    fun closeCheckout() {
        _showCheckoutSheet.value = false
    }

    fun placeCurrentOrder() {
        viewModelScope.launch {
            val fulfillment = _selectedFulfillment.value
            val record = repository.placeOrder(fulfillment)
            _showCheckoutSheet.value = false
            if (fulfillment == OrderFulfillmentType.DRIVE_THRU) {
                _showDriveThruCode.value = true
            }
        }
    }

    fun saveDrinkAsFavorite(name: String, drink: DrinkItem, customization: CustomizationOptions) {
        viewModelScope.launch {
            repository.saveFavoriteDrink(
                drinkName = name.ifBlank { drink.name },
                category = drink.category.displayName,
                price = customization.calculateTotalPrice(drink.basePrice),
                customization = customization
            )
        }
    }

    fun reloadCard(amount: Double) {
        viewModelScope.launch {
            repository.reloadStarbucksCard(amount)
            _showReloadSheet.value = false
        }
    }

    fun toggleReloadSheet(show: Boolean) {
        _showReloadSheet.value = show
    }

    fun toggleDriveThruModal(show: Boolean) {
        _showDriveThruCode.value = show
    }

    fun toggleAiAssistant(show: Boolean) {
        _showAiAssistant.value = show
    }

    fun sendAiPrompt(prompt: String) {
        if (prompt.isBlank()) return
        val current = _aiMessages.value.toMutableList()
        current.add(AiChatMessage("user", prompt))
        _aiMessages.value = current
        _isAiLoading.value = true

        viewModelScope.launch {
            val response = assistService.getCoffeeRecommendation(prompt)
            val updated = _aiMessages.value.toMutableList()
            updated.add(AiChatMessage("assistant", response))
            _aiMessages.value = updated
            _isAiLoading.value = false
        }
    }

    fun advanceDeliveryProgress() {
        viewModelScope.launch {
            repository.advanceOrderStatus()
        }
    }

    fun selectStore(store: StarbucksStore) {
        repository.selectStore(store)
    }

    fun toggleOfferSave(offerId: String) {
        repository.toggleOfferSaved(offerId)
    }

    fun removeFromCart(cartItemId: String) {
        repository.removeFromCart(cartItemId)
    }
}
