package com.example.bookhub.model
// data always used in parimary constructor
data class Book(
    val bookId: String,
    val bookName:String,
    val bookAuthor: String,
    val bookRating: String,
    val bookPrice: String,
    // bookImage : Int -->because it store  id of book
    val bookImage: String

)