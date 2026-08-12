package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        FavoriteDrinkEntity::class,
        OrderEntity::class,
        StarbucksCardEntity::class,
        GiftCardEntity::class,
        NotificationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class StarbucksDatabase : RoomDatabase() {
    abstract fun starbucksDao(): StarbucksDao

    companion object {
        @Volatile
        private var INSTANCE: StarbucksDatabase? = null

        fun getDatabase(context: Context): StarbucksDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StarbucksDatabase::class.java,
                    "starbucks_super_app_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
