package com.example.data

import com.example.data.models.*

object StarbucksData {

    val sampleStores = listOf(
        StarbucksStore(
            id = "store_1",
            name = "Starbucks — Main Street",
            distanceMiles = 0.4,
            address = "742 Main St, Downtown",
            openStatus = "Open until 9:00 PM",
            busyLevel = "Moderate",
            estimatedWaitMinutes = 7,
            hasPickup = true,
            hasDriveThru = true,
            hasDelivery = true,
            hasWifi = true,
            hasSeating = true,
            isFavorite = true,
            lat = 37.7749,
            lng = -122.4194
        ),
        StarbucksStore(
            id = "store_2",
            name = "Starbucks — Reserve Roastery / 5th Ave",
            distanceMiles = 1.2,
            address = "1120 5th Ave, Center City",
            openStatus = "Open until 10:00 PM",
            busyLevel = "Busy",
            estimatedWaitMinutes = 12,
            hasPickup = true,
            hasDriveThru = false,
            hasDelivery = true,
            hasWifi = true,
            hasSeating = true,
            isFavorite = false,
            lat = 37.7833,
            lng = -122.4167
        ),
        StarbucksStore(
            id = "store_3",
            name = "Starbucks — Westside Plaza Drive-Thru",
            distanceMiles = 2.1,
            address = "450 Westside Hwy",
            openStatus = "Open 24 Hours",
            busyLevel = "Light",
            estimatedWaitMinutes = 4,
            hasPickup = true,
            hasDriveThru = true,
            hasDelivery = false,
            hasWifi = true,
            hasSeating = false,
            isFavorite = false,
            lat = 37.7650,
            lng = -122.4300
        )
    )

    val sampleDrinks = listOf(
        DrinkItem(
            id = "drink_1",
            name = "Iced Caramel Macchiato",
            category = DrinkCategory.COLD_COFFEE,
            basePrice = 5.95,
            calories = 250,
            caffeineMg = 150,
            description = "Freshly steamed milk with vanilla-flavored syrup marked with espresso and topped with a caramel drizzle.",
            defaultOptions = CustomizationOptions(
                size = DrinkSize.GRANDE,
                milk = MilkOption.OAT_MILK,
                shots = 2,
                syrup = "Vanilla",
                sauce = "Caramel Drizzle",
                ice = IceLevel.LIGHT_ICE
            ),
            isPopular = true,
            isSeasonal = false
        ),
        DrinkItem(
            id = "drink_2",
            name = "Iced Brown Sugar Oatmilk Shaken Espresso",
            category = DrinkCategory.COLD_COFFEE,
            basePrice = 6.45,
            calories = 120,
            caffeineMg = 255,
            description = "Starbucks® Blonde espresso, brown sugar and cinnamon shaken together, and topped with oatmilk.",
            defaultOptions = CustomizationOptions(
                size = DrinkSize.GRANDE,
                milk = MilkOption.OAT_MILK,
                shots = 3,
                syrup = "Brown Sugar",
                topping = "Cinnamon Powder",
                ice = IceLevel.REGULAR_ICE
            ),
            isPopular = true,
            isSeasonal = true
        ),
        DrinkItem(
            id = "drink_3",
            name = "Strawberry Açaí Starbucks Refreshers®",
            category = DrinkCategory.REFRESHERS,
            basePrice = 5.25,
            calories = 100,
            caffeineMg = 45,
            description = "Accented by passion fruit & acai notes, shaken with ice and real strawberry slices.",
            defaultOptions = CustomizationOptions(
                size = DrinkSize.GRANDE,
                milk = MilkOption.WHOLE_MILK,
                shots = 0,
                syrup = "None",
                ice = IceLevel.REGULAR_ICE
            ),
            isPopular = true,
            isSeasonal = false
        ),
        DrinkItem(
            id = "drink_4",
            name = "Pike Place® Roast",
            category = DrinkCategory.HOT_COFFEE,
            basePrice = 3.65,
            calories = 5,
            caffeineMg = 310,
            description = "Well-rounded with subtle notes of cocoa and toasted nuts balancing the smooth mouthfeel.",
            defaultOptions = CustomizationOptions(
                size = DrinkSize.GRANDE,
                milk = MilkOption.TWO_PERCENT,
                shots = 0,
                syrup = "None",
                ice = IceLevel.NO_ICE
            ),
            isPopular = false,
            isSeasonal = false
        ),
        DrinkItem(
            id = "drink_5",
            name = "Matcha Tea Latte",
            category = DrinkCategory.TEA,
            basePrice = 5.45,
            calories = 240,
            caffeineMg = 80,
            description = "Smooth and creamy matcha sweetened finely and served with steamed milk.",
            defaultOptions = CustomizationOptions(
                size = DrinkSize.GRANDE,
                milk = MilkOption.OAT_MILK,
                shots = 0,
                syrup = "Classic Syrup"
            ),
            isPopular = true,
            isSeasonal = false
        ),
        DrinkItem(
            id = "drink_6",
            name = "Bacon, Gouda & Egg Sandwich",
            category = DrinkCategory.BREAKFAST,
            basePrice = 5.95,
            calories = 370,
            caffeineMg = 0,
            description = "Sizzling applewood-smoked bacon, aged Gouda and a parmesan frittata on an artisan roll.",
            defaultOptions = CustomizationOptions(),
            isPopular = true,
            isSeasonal = false
        ),
        DrinkItem(
            id = "drink_7",
            name = "Butter Croissant",
            category = DrinkCategory.BAKERY,
            basePrice = 3.95,
            calories = 260,
            caffeineMg = 0,
            description = "100% butter croissant with flaky outer layers and a soft tender interior.",
            defaultOptions = CustomizationOptions(),
            isPopular = false,
            isSeasonal = false
        ),
        DrinkItem(
            id = "drink_8",
            name = "Tomato & Mozzarella Focaccia",
            category = DrinkCategory.LUNCH,
            basePrice = 7.45,
            calories = 360,
            caffeineMg = 0,
            description = "Roasted tomatoes, mozzarella, spinach and basil pesto on toasted focaccia bread.",
            defaultOptions = CustomizationOptions(),
            isPopular = false,
            isSeasonal = false
        ),
        DrinkItem(
            id = "drink_9",
            name = "Chocolate Chip Cookie",
            category = DrinkCategory.SNACKS,
            basePrice = 2.95,
            calories = 370,
            caffeineMg = 0,
            description = "Semisweet chocolate chunks folded into a soft, golden dough.",
            defaultOptions = CustomizationOptions(),
            isPopular = false,
            isSeasonal = false
        )
    )

    val sampleMerchandise = listOf(
        MerchandiseItem(
            id = "merch_1",
            title = "Starbucks Stainless Steel Tumbler (24 oz)",
            category = "Tumblers",
            price = 29.95,
            description = "Double-wall vacuum insulated emerald green cold cup tumbler."
        ),
        MerchandiseItem(
            id = "merch_2",
            title = "Pike Place Whole Bean Coffee (1 lb)",
            category = "Whole Bean",
            price = 14.95,
            description = "Medium roast whole bean coffee with notes of cocoa and toasted nuts."
        ),
        MerchandiseItem(
            id = "merch_3",
            title = "Ceramic Siren Reserve Mug (14 oz)",
            category = "Mugs",
            price = 18.50,
            description = "Sleek matte green ceramic mug with embossed Siren crest."
        ),
        MerchandiseItem(
            id = "merch_4",
            title = "Veranda Blend Blonde Roast Pods (24 pk)",
            category = "Pods",
            price = 19.99,
            description = "Subtle, soft flavor notes of toasted malt and baking chocolate."
        )
    )

    val sampleOffers = listOf(
        RewardOffer(
            id = "offer_1",
            title = "Double Star Days Are Back",
            description = "Earn 2x Stars on all orders placed through the Starbucks app today.",
            starCost = 0,
            iconName = "stars",
            isSaved = true
        ),
        RewardOffer(
            id = "offer_2",
            title = "Afternoon Pick-Me-Up",
            description = "20% off any handcrafted iced espresso drink after 2:00 PM.",
            starCost = 0,
            iconName = "local_cafe",
            isSaved = false
        ),
        RewardOffer(
            id = "offer_3",
            title = "Breakfast Combo Bonus",
            description = "Earn 100 Bonus Stars when you pair any warm breakfast item with a Grande coffee.",
            starCost = 0,
            iconName = "bakery_dining",
            isSaved = false
        )
    )

    val sampleChallenges = listOf(
        BonusChallenge(
            id = "chal_1",
            title = "Iced Coffee Explorer",
            progress = "2 of 3 orders completed",
            starsReward = 150,
            isCompleted = false
        ),
        BonusChallenge(
            id = "chal_2",
            title = "Weekend Morning Routine",
            progress = "Completed!",
            starsReward = 200,
            isCompleted = true
        )
    )

    val sampleGiftCards = listOf(
        GiftCardItem(
            id = "gc_1",
            title = "Starbucks Coffee Love",
            themeName = "Classic Siren Green",
            balance = 25.00,
            code = "SBX-8829-4910"
        ),
        GiftCardItem(
            id = "gc_2",
            title = "Happy Birthday Brew",
            themeName = "Gold Celebrations",
            balance = 50.00,
            code = "SBX-9901-2241"
        )
    )

    val sampleNotifications = listOf(
        NotificationModel(
            id = "notif_1",
            title = "☕ YOUR ORDER IS READY",
            body = "Iced Caramel Macchiato is ready at Starbucks — Main Street pickup counter.",
            timeAgo = "10 mins ago"
        ),
        NotificationModel(
            id = "notif_2",
            title = "⭐ REWARDS UPDATED",
            body = "You earned +120 Stars on your last purchase! Current balance: 8,420 Stars.",
            timeAgo = "2 hours ago"
        ),
        NotificationModel(
            id = "notif_3",
            title = "🎁 NEW MEMBER OFFER",
            body = "Special Double Star Day activated for your account.",
            timeAgo = "Yesterday"
        )
    )
}
