package com.example.data

import android.content.Context
import com.example.data.db.*
import com.example.data.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class StarbucksRepository(context: Context) {
    private val db = StarbucksDatabase.getDatabase(context)
    private val dao = db.starbucksDao()

    // In-memory runtime cart state
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // User rewards & account status
    private val _starsCount = MutableStateFlow(8420)
    val starsCount: StateFlow<Int> = _starsCount.asStateFlow()

    // Selected Store
    private val _selectedStore = MutableStateFlow(StarbucksData.sampleStores.first())
    val selectedStore: StateFlow<StarbucksStore> = _selectedStore.asStateFlow()

    // Active order tracking (for Delivery / Pickup)
    private val _activeOrder = MutableStateFlow<OrderRecord?>(
        OrderRecord(
            orderId = "SBX-1082",
            items = listOf(
                CartItem(
                    id = "cart_init_1",
                    drinkItem = StarbucksData.sampleDrinks.first(),
                    customization = StarbucksData.sampleDrinks.first().defaultOptions,
                    quantity = 1
                )
            ),
            storeName = StarbucksData.sampleStores.first().name,
            fulfillmentType = OrderFulfillmentType.DELIVERY,
            totalAmount = 6.25,
            starsEarned = 120,
            estimatedMinutes = 8,
            status = OrderStatus.PREPARING
        )
    )
    val activeOrder: StateFlow<OrderRecord?> = _activeOrder.asStateFlow()

    // Starbucks Card
    val starbucksCardFlow: Flow<StarbucksCardDetails> = dao.getStarbucksCard().map { entity ->
        if (entity != null) {
            StarbucksCardDetails(
                cardNumber = entity.cardNumber,
                balance = entity.balance,
                autoReloadEnabled = entity.autoReloadEnabled
            )
        } else {
            StarbucksCardDetails()
        }
    }

    // Favorite drinks
    val favoriteDrinksFlow: Flow<List<FavoriteDrinkEntity>> = dao.getFavoriteDrinks()

    // Order History
    val orderHistoryFlow: Flow<List<OrderEntity>> = dao.getAllOrders()

    // Notifications
    val notificationsFlow: Flow<List<NotificationEntity>> = dao.getNotifications()

    // Saved offers state
    private val _offers = MutableStateFlow(StarbucksData.sampleOffers)
    val offers: StateFlow<List<RewardOffer>> = _offers.asStateFlow()

    fun selectStore(store: StarbucksStore) {
        _selectedStore.value = store
    }

    fun addToCart(drink: DrinkItem, customization: CustomizationOptions, quantity: Int = 1) {
        val current = _cartItems.value.toMutableList()
        val newItem = CartItem(
            id = System.currentTimeMillis().toString(),
            drinkItem = drink,
            customization = customization,
            quantity = quantity
        )
        current.add(newItem)
        _cartItems.value = current
    }

    fun removeFromCart(cartItemId: String) {
        val current = _cartItems.value.toMutableList()
        current.removeAll { it.id == cartItemId }
        _cartItems.value = current
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    suspend fun placeOrder(
        fulfillmentType: OrderFulfillmentType,
        customCardBalanceDeduction: Double? = null
    ): OrderRecord {
        val items = _cartItems.value.ifEmpty {
            listOf(
                CartItem(
                    id = "cart_demo",
                    drinkItem = StarbucksData.sampleDrinks[0],
                    customization = StarbucksData.sampleDrinks[0].defaultOptions
                )
            )
        }
        val subtotal = items.sumOf { it.totalPrice }
        val tax = subtotal * 0.08
        val total = subtotal + tax
        val stars = (total * 20).toInt()

        val newRecord = OrderRecord(
            orderId = "SBX-${(1000..9999).random()}",
            items = items,
            storeName = _selectedStore.value.name,
            fulfillmentType = fulfillmentType,
            totalAmount = total,
            starsEarned = stars,
            estimatedMinutes = if (fulfillmentType == OrderFulfillmentType.DELIVERY) 22 else 7,
            status = OrderStatus.PREPARING
        )

        // Save order in Room
        val itemsSummary = items.joinToString(", ") { "${it.quantity}x ${it.drinkItem.name}" }
        dao.insertOrder(
            OrderEntity(
                orderId = newRecord.orderId,
                itemsSummary = itemsSummary,
                storeName = newRecord.storeName,
                fulfillmentType = fulfillmentType.name,
                totalAmount = newRecord.totalAmount,
                starsEarned = newRecord.starsEarned,
                status = newRecord.status.name,
                timestamp = System.currentTimeMillis()
            )
        )

        // Update active order
        _activeOrder.value = newRecord
        _starsCount.value += stars

        // Deduct balance from card if stored
        val card = dao.getStarbucksCard()
        val currentBal = 42.80
        val newBal = (currentBal - total).coerceAtLeast(0.0)
        dao.insertOrUpdateCard(
            StarbucksCardEntity(
                id = 1,
                balance = newBal,
                cardNumber = "•••• 1842",
                autoReloadEnabled = false
            )
        )

        // Clear cart
        clearCart()

        // Insert notification
        dao.insertNotification(
            NotificationEntity(
                id = System.currentTimeMillis().toString(),
                title = "☕ ORDER PLACED (${newRecord.orderId})",
                body = "Your order at ${newRecord.storeName} is being prepared. Est time: ${newRecord.estimatedMinutes} mins.",
                timeAgo = "Just now"
            )
        )

        return newRecord
    }

    suspend fun saveFavoriteDrink(drinkName: String, category: String, price: Double, customization: CustomizationOptions) {
        val entity = FavoriteDrinkEntity(
            id = System.currentTimeMillis().toString(),
            name = drinkName,
            category = category,
            basePrice = price,
            size = customization.size.label,
            milk = customization.milk.displayName,
            syrup = customization.syrup
        )
        dao.insertFavoriteDrink(entity)
    }

    suspend fun reloadStarbucksCard(amount: Double) {
        val currentCard = dao.getStarbucksCard()
        val currentBal = 42.80
        val updatedBal = currentBal + amount
        dao.insertOrUpdateCard(
            StarbucksCardEntity(
                id = 1,
                balance = updatedBal,
                cardNumber = "•••• 1842",
                autoReloadEnabled = false
            )
        )
    }

    fun toggleOfferSaved(offerId: String) {
        val list = _offers.value.map { offer ->
            if (offer.id == offerId) offer.copy(isSaved = !offer.isSaved) else offer
        }
        _offers.value = list
    }

    suspend fun advanceOrderStatus() {
        val current = _activeOrder.value ?: return
        val nextStatus = when (current.status) {
            OrderStatus.PREPARING -> OrderStatus.PICKED_UP
            OrderStatus.PICKED_UP -> OrderStatus.ON_THE_WAY
            OrderStatus.ON_THE_WAY -> OrderStatus.DELIVERED
            OrderStatus.DELIVERED -> OrderStatus.PREPARING
        }
        _activeOrder.value = current.copy(status = nextStatus)
        dao.updateOrderStatus(current.orderId, nextStatus.name)
    }
}
