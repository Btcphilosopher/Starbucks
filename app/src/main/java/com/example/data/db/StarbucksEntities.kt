package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_drinks")
data class FavoriteDrinkEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val basePrice: Double,
    val size: String,
    val milk: String,
    val syrup: String,
    val isUsual: Boolean = false
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val orderId: String,
    val itemsSummary: String,
    val storeName: String,
    val fulfillmentType: String,
    val totalAmount: Double,
    val starsEarned: Int,
    val status: String,
    val timestamp: Long
)

@Entity(tableName = "starbucks_card")
data class StarbucksCardEntity(
    @PrimaryKey val id: Int = 1,
    val balance: Double,
    val cardNumber: String,
    val autoReloadEnabled: Boolean
)

@Entity(tableName = "gift_cards")
data class GiftCardEntity(
    @PrimaryKey val id: String,
    val title: String,
    val balance: Double,
    val code: String,
    val recipientEmail: String
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val title: String,
    val body: String,
    val timeAgo: String,
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
