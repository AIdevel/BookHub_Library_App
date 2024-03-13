package com.example.bookhub.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bookhub.model.Book

@Dao
interface BookDao {
    // used for adding Data
    @Insert
    fun insertBook(bookEntity: BookEntity)

    // Used for deleting data
    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Query("SELECT * FROM books")
   fun getAllBook(): List<BookEntity>

    // book_id= :bookId indicate that the value present at the below that recieved from some function
    @Query("SELECT * FROM books WHERE book_id= :bookId")
   fun getBookById(bookId:String): BookEntity


}