package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.StarbucksHouseGreen
import com.example.ui.theme.StarbucksTheme
import com.example.ui.theme.StarbucksWarmCream
import com.example.viewmodel.MainTab
import com.example.viewmodel.StarbucksViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: StarbucksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StarbucksTheme {
                val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
                val starsCount by viewModel.starsCount.collectAsStateWithLifecycle()
                val cardDetails by viewModel.starbucksCard.collectAsStateWithLifecycle()
                val selectedStore by viewModel.selectedStore.collectAsStateWithLifecycle()
                val activeOrder by viewModel.activeOrder.collectAsStateWithLifecycle()
                val favoriteDrinks by viewModel.favoriteDrinks.collectAsStateWithLifecycle()
                val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
                val offers by viewModel.offers.collectAsStateWithLifecycle()
                val orderHistory by viewModel.orderHistory.collectAsStateWithLifecycle()

                val customizingDrink by viewModel.customizingDrink.collectAsStateWithLifecycle()
                val currentCustomization by viewModel.currentCustomization.collectAsStateWithLifecycle()
                val showCheckoutSheet by viewModel.showCheckoutSheet.collectAsStateWithLifecycle()
                val showDriveThruCode by viewModel.showDriveThruCode.collectAsStateWithLifecycle()
                val showReloadSheet by viewModel.showReloadSheet.collectAsStateWithLifecycle()
                val showAiAssistant by viewModel.showAiAssistant.collectAsStateWithLifecycle()
                val aiMessages by viewModel.aiMessages.collectAsStateWithLifecycle()
                val isAiLoading by viewModel.isAiLoading.collectAsStateWithLifecycle()
                val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color.White,
                            contentColor = StarbucksHouseGreen,
                            tonalElevation = 8.dp,
                            modifier = Modifier.testTag("main_bottom_nav")
                        ) {
                            NavigationBarItem(
                                selected = currentTab == MainTab.HOME,
                                onClick = { viewModel.selectTab(MainTab.HOME) },
                                icon = { Icon(if (currentTab == MainTab.HOME) Icons.Filled.Home else Icons.Outlined.Home, contentDescription = "Home") },
                                label = { Text("Home", fontWeight = FontWeight.Bold, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(selectedIconColor = StarbucksHouseGreen, indicatorColor = com.example.ui.theme.StarbucksLightGreen),
                                modifier = Modifier.testTag("nav_home")
                            )

                            NavigationBarItem(
                                selected = currentTab == MainTab.ORDER,
                                onClick = { viewModel.selectTab(MainTab.ORDER) },
                                icon = { Icon(if (currentTab == MainTab.ORDER) Icons.Filled.LocalCafe else Icons.Outlined.LocalCafe, contentDescription = "Order") },
                                label = { Text("Order", fontWeight = FontWeight.Bold, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(selectedIconColor = StarbucksHouseGreen, indicatorColor = com.example.ui.theme.StarbucksLightGreen),
                                modifier = Modifier.testTag("nav_order")
                            )

                            NavigationBarItem(
                                selected = currentTab == MainTab.REWARDS,
                                onClick = { viewModel.selectTab(MainTab.REWARDS) },
                                icon = { Icon(if (currentTab == MainTab.REWARDS) Icons.Filled.Star else Icons.Outlined.StarBorder, contentDescription = "Rewards") },
                                label = { Text("Rewards", fontWeight = FontWeight.Bold, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(selectedIconColor = StarbucksHouseGreen, indicatorColor = com.example.ui.theme.StarbucksLightGreen),
                                modifier = Modifier.testTag("nav_rewards")
                            )

                            NavigationBarItem(
                                selected = currentTab == MainTab.STORES,
                                onClick = { viewModel.selectTab(MainTab.STORES) },
                                icon = { Icon(if (currentTab == MainTab.STORES) Icons.Filled.LocationOn else Icons.Outlined.LocationOn, contentDescription = "Stores") },
                                label = { Text("Stores", fontWeight = FontWeight.Bold, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(selectedIconColor = StarbucksHouseGreen, indicatorColor = com.example.ui.theme.StarbucksLightGreen),
                                modifier = Modifier.testTag("nav_stores")
                            )

                            NavigationBarItem(
                                selected = currentTab == MainTab.MORE,
                                onClick = { viewModel.selectTab(MainTab.MORE) },
                                icon = { Icon(if (currentTab == MainTab.MORE) Icons.Filled.MoreHoriz else Icons.Outlined.MoreHoriz, contentDescription = "More") },
                                label = { Text("More", fontWeight = FontWeight.Bold, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(selectedIconColor = StarbucksHouseGreen, indicatorColor = com.example.ui.theme.StarbucksLightGreen),
                                modifier = Modifier.testTag("nav_more")
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (currentTab) {
                            MainTab.HOME -> HomeScreen(
                                starsCount = starsCount,
                                cardBalance = cardDetails.balance,
                                selectedStore = selectedStore,
                                activeOrder = activeOrder,
                                favoriteDrinks = favoriteDrinks,
                                offers = offers,
                                onOrderUsualClick = { viewModel.orderUsual() },
                                onPayClick = { viewModel.selectTab(MainTab.MORE) },
                                onNavigateTab = { tab -> viewModel.selectTab(tab) },
                                onSelectDrink = { drink -> viewModel.startCustomizingDrink(drink) },
                                onAdvanceOrderStep = { viewModel.advanceDeliveryProgress() },
                                onNotificationClick = { viewModel.selectTab(MainTab.MORE) },
                                onAiAssistantClick = { viewModel.toggleAiAssistant(true) }
                            )

                            MainTab.ORDER -> OrderScreen(
                                selectedCategory = selectedCategory,
                                cartItems = cartItems,
                                onSelectCategory = { cat -> viewModel.selectCategory(cat) },
                                onSelectDrink = { drink -> viewModel.startCustomizingDrink(drink) },
                                onOpenCheckout = { viewModel.openCheckout() }
                            )

                            MainTab.REWARDS -> RewardsScreen(
                                starsCount = starsCount,
                                offers = offers,
                                onToggleSaveOffer = { id -> viewModel.toggleOfferSave(id) }
                            )

                            MainTab.STORES -> StoresScreen(
                                selectedStore = selectedStore,
                                onSelectStore = { store -> viewModel.selectStore(store) },
                                onDriveThruOrderClick = {
                                    viewModel.setFulfillmentType(com.example.data.models.OrderFulfillmentType.DRIVE_THRU)
                                    viewModel.orderUsual()
                                }
                            )

                            MainTab.MORE -> MoreScreen(
                                starbucksCard = cardDetails,
                                orderHistory = orderHistory,
                                onReloadCardClick = { viewModel.toggleReloadSheet(true) },
                                onReorderUsual = { viewModel.orderUsual() }
                            )
                        }

                        // Customizer Sheet
                        if (customizingDrink != null) {
                            CustomizerBottomSheet(
                                drinkItem = customizingDrink!!,
                                initialCustomization = currentCustomization,
                                onDismiss = { viewModel.closeCustomizer() },
                                onAddToCart = { cust ->
                                    viewModel.updateCustomization(cust)
                                    viewModel.addCustomizedDrinkToCart()
                                },
                                onSaveFavorite = { name, cust ->
                                    viewModel.saveDrinkAsFavorite(name, customizingDrink!!, cust)
                                }
                            )
                        }

                        // Checkout Sheet
                        if (showCheckoutSheet) {
                            CheckoutBottomSheet(
                                cartItems = cartItems,
                                selectedStore = selectedStore,
                                selectedFulfillment = viewModel.selectedFulfillment.value,
                                cardBalance = cardDetails.balance,
                                onFulfillmentChange = { f -> viewModel.setFulfillmentType(f) },
                                onRemoveItem = { id -> viewModel.removeFromCart(id) },
                                onPlaceOrder = { viewModel.placeCurrentOrder() },
                                onDismiss = { viewModel.closeCheckout() }
                            )
                        }

                        // Drive Thru Dialog
                        if (showDriveThruCode) {
                            DriveThruCodeDialog(
                                orderNumber = "DT-${(100..999).random()}",
                                onDismiss = { viewModel.toggleDriveThruModal(false) }
                            )
                        }

                        // Reload Card Sheet
                        if (showReloadSheet) {
                            ReloadCardBottomSheet(
                                currentBalance = cardDetails.balance,
                                onReloadConfirm = { amt -> viewModel.reloadCard(amt) },
                                onDismiss = { viewModel.toggleReloadSheet(false) }
                            )
                        }

                        // AI Starbucks Assist Dialog
                        if (showAiAssistant) {
                            AiAssistantDialog(
                                messages = aiMessages,
                                isLoading = isAiLoading,
                                onSendMessage = { text -> viewModel.sendAiPrompt(text) },
                                onDismiss = { viewModel.toggleAiAssistant(false) }
                            )
                        }
                    }
                }
            }
        }
    }
}
