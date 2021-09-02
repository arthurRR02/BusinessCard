package com.example.businesscard.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BusinessCardEntity::class], version = 1)
abstract class BusinessCardDatabase: RoomDatabase() {

    abstract fun businessCardDao(): BusinessCardDao
}