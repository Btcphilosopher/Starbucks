package com.example.data.models

import androidx.compose.ui.graphics.Color

enum class DrinkCategory(val displayName: String) {
    HOT_COFFEE("Hot Coffee"),
    COLD_COFFEE("Cold Coffee"),
    REFRESHERS("Refreshers"),
    TEA("Tea"),
    BREAKFAST("Breakfast"),
    BAKERY("Bakery"),
    LUNCH("Lunch"),
    SNACKS("Snacks"),
    MERCHANDISE("Merchandise")
}

enum class DrinkSize(val label: String, val volume: String, val priceModifier: Double) {
    SHORT("Short", "8 fl oz", -0.50),
    TALL("Tall", "12 fl oz", -0.25),
    GRANDE("Grande", "16 fl oz", 0.00),
    VENTI("Venti", "24 fl oz", 0.75),
    TRENTA("Trenta", "30 fl oz", 1.25)
}

enum class MilkOption(val displayName: String, val extraCost: Double) {
    WHOLE_MILK("Whole Milk", 0.0),
    SKIM_MILK("Skim Milk", 0.0),
    TWO_PERCENT("2% Milk", 0.0),
    OAT_MILK("Oat Milk", 0.80),
    ALMOND_MILK("Almond Milk", 0.80),
    SOY_MILK("Soy Milk", 0.80),
    COCONUT_MILK("Coconut Milk", 0.80)
}

enum class IceLevel(val displayName: String) {
    EXTRA_ICE("Extra Ice"),
    REGULAR_ICE("Regular Ice"),
    LIGHT_ICE("Light Ice"),
    NO_ICE("No Ice")
}

enum class SweetnessLevel(val displayName: String) {
    UNSWEETENED("Unsweetened"),
    HALF_SWEET("Half Sweet (2 pumps)"),
    REGULAR_SWEET("Regular Sweet (4 pumps)"),
    EXTRA_SWEET("Extra Sweet (6 pumps)")
}

data class CustomizationOptions(
    val size: DrinkSize = DrinkSize.GRANDE,
    val milk: MilkOption = MilkOption.OAT_MILK,
    val shots: Int = 2,
    val syrup: String = "Vanilla",
    val sauce: String = "Caramel Drizzle",
    val topping: String = "Whipped Cream",
    val ice: IceLevel = IceLevel.LIGHT_ICE,
    val temperature: String = "Cold",
    val sweetness: SweetnessLevel = SweetnessLevel.REGULAR_SWEET
) {
    fun calculateTotalPrice(basePrice: Double): Double {
        var total = basePrice + size.priceModifier + milk.extraCost
        if (shots > 2) total += (shots - 2) * 1.00
        if (syrup.isNotEmpty() && syrup != "None") total += 0.60
        if (sauce.isNotEmpty() && sauce != "None") total += 0.60
        return String.format("%.2f", total).toDouble()
    }
}

data class DrinkItem(
    val id: String,
    val name: String,
    val category: DrinkCategory,
    val basePrice: Double,
    val calories: Int,
    val caffeineMg: Int,
    val description: String,
    val defaultOptions: CustomizationOptions = CustomizationOptions(),
    val imageUrl: String = "",
    val isPopular: Boolean = false,
    val isSeasonal: Boolean = false
)

data class CartItem(
    val id: String,
    val drinkItem: DrinkItem,
    val customization: CustomizationOptions,
    val quantity: Int = 1
) {
    val unitPrice: Double get() = customization.calculateTotalPrice(drinkItem.basePrice)
    val totalPrice: Double get() = unitPrice * quantity
}

enum class OrderFulfillmentType {
    PICKUP,
    DRIVE_THRU,
    DELIVERY
}

enum class OrderStatus {
    PREPARING,
    PICKED_UP,
    ON_THE_WAY,
    DELIVERED
}

data class OrderRecord(
    val orderId: String,
    val items: List<CartItem>,
    val storeName: String,
    val fulfillmentType: OrderFulfillmentType,
    val totalAmount: Double,
    val starsEarned: Int,
    val estimatedMinutes: Int,
    val status: OrderStatus = OrderStatus.PREPARING,
    val timestamp: Long = System.currentTimeMillis()
)

data class StarbucksStore(
    val id: String,
    val name: String,
    val distanceMiles: Double,
    val address: String,
    val openStatus: String,
    val busyLevel: String,
    val estimatedWaitMinutes: Int,
    val hasPickup: Boolean = true,
    val hasDriveThru: Boolean = true,
    val hasDelivery: Boolean = true,
    val hasWifi: Boolean = true,
    val hasSeating: Boolean = true,
    val hasNitroColdBrew: Boolean = true,
    val isFavorite: Boolean = false,
    val lat: Double = 37.7749,
    val lng: Double = -122.4194
)

data class RewardOffer(
    val id: String,
    val title: String,
    val description: String,
    val starCost: Int,
    val iconName: String,
    val isSaved: Boolean = false
)

data class BonusChallenge(
    val id: String,
    val title: String,
    val progress: String,
    val starsReward: Int,
    val isCompleted: Boolean = false
)

data class StarbucksCardDetails(
    val cardNumber: String = "•••• 1842",
    val balance: Double = 42.80,
    val autoReloadEnabled: Boolean = false,
    val autoReloadAmount: Double = 25.00
)

data class GiftCardItem(
    val id: String,
    val title: String,
    val themeName: String,
    val balance: Double,
    val recipientEmail: String = "",
    val message: String = "",
    val code: String = ""
)

data class MerchandiseItem(
    val id: String,
    val title: String,
    val category: String,
    val price: Double,
    val description: String,
    val imageUrl: String = ""
)

data class NotificationModel(
    val id: String,
    val title: String,
    val body: String,
    val timeAgo: String,
    val isRead: Boolean = false
)

data class CoffeeProfile(
    val userName: String = "Tom",
    val usualDrinkName: String = "Iced Caramel Macchiato",
    val preferredSize: String = "Grande",
    val preferredMilk: String = "Oat Milk",
    val preferredFlavour: String = "Vanilla & Caramel",
    val favoriteStoreName: String = "Starbucks — 0.4 miles",
    val totalOrdersCount: Int = 42,
    val recommendation: String = "Iced Brown Sugar Oatmilk Shaken Espresso"
)
