package com.example.bookhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// @ -> known as Annotation

/* this define the the Table_Name is books otherwise take the table as
 the name of class by " Default "
 */
@Entity(tableName = "books")

data class BookEntity(
    // @Primary Key should be always be unique
    @PrimaryKey val book_id:Int,
    // used to add coloumn in table
    // @ColumnInfo(name_of_Column)

    @ColumnInfo(name = "book_name") val bookName: String,
    @ColumnInfo(name = "book_author") val bookAuthor: String,
    @ColumnInfo(name = "book_price") val bookPrice: String,
    @ColumnInfo(name = "book_rating") val bookRating: String,
    @ColumnInfo(name = "book_desc") val bookDesc: String,
    @ColumnInfo(name = "book_image") val bookImage: String
)