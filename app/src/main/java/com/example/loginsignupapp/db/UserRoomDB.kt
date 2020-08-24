package com.example.loginsignupapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
public abstract class UserRoomDB : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: UserRoomDB? = null

        fun getUserDatabase(context: Context): UserRoomDB {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDB::class.java,
                    "user_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

}