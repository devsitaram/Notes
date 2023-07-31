package com.record.notes.features.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Customer::class], exportSchema = false, version = 1)
abstract class DatabaseHelper : RoomDatabase() {

    companion object {
//        private const val DB_NAME: String = "CustomerDatabase"
        private val DB_NAME: String = "BookDatabase"
        private var instance: DatabaseHelper? = null

        // this methods are FIFO data
        @Synchronized
        fun getInstance(context: Context?): DatabaseHelper? {
            if (instance == null) {
                instance = databaseBuilder(context!!, DatabaseHelper::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }

    abstract fun CustomerDao(): CustomerDao
}