package com.example.bookhub.database

import androidx.room.Database
import androidx.room.RoomDatabase

// we declare that database base used in which BookEntity class was introduce
// & give version =1 to Database
@Database(entities = [BookEntity::class], version = 1)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao

}