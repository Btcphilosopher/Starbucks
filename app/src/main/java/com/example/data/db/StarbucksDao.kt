package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StarbucksDao {
    @Query("SELECT * FROM favorite_drinks")
    fun getFavoriteDrinks(): Flow<List<FavoriteDrinkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteDrink(drink: FavoriteDrinkEntity)

    @Query("DELETE FROM favorite_drinks WHERE id = :id")
    suspend fun deleteFavoriteDrink(id: String)

    @Query("SELECT * FROM orders ORDER BY timestamp DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("UPDATE orders SET status = :status WHERE orderId = :orderId")
    suspend fun updateOrderStatus(orderId: String, status: String)

    @Query("SELECT * FROM starbucks_card WHERE id = 1")
    fun getStarbucksCard(): Flow<StarbucksCardEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCard(card: StarbucksCardEntity)

    @Query("SELECT * FROM gift_cards")
    fun getGiftCards(): Flow<List<GiftCardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGiftCard(card: GiftCardEntity)

    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    fun getNotifications(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)
}
