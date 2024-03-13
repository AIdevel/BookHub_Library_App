package com.example.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.R
import com.example.bookhub.database.BookEntity
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context, val bookList:List<BookEntity>): RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {
    class FavouriteViewHolder(view: View):RecyclerView.ViewHolder(view){
        val txtBookName: TextView =view.findViewById(R.id.FavBookName)
        val txtBookAuthor: TextView =view.findViewById(R.id.FavBookAuthor)
        val txtBookPrice: TextView =view.findViewById(R.id.FavBookPrice)
        val txtBookRating: TextView = view.findViewById(R.id.FavBookRating)
        val txtBookImage: ImageView = view.findViewById(R.id.FavBookIcon)
        val llContent: RelativeLayout = view.findViewById(R.id.llFavContent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
val view =LayoutInflater.from(parent.context)
    .inflate(R.layout.recycler_favourite_single_row,parent ,false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size

    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {

        val book=bookList[position]

        holder.txtBookName.text = book.bookName
        holder.txtBookAuthor.text =book.bookAuthor
        holder.txtBookPrice.text =book.bookPrice
        holder.txtBookRating.text =book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.defaultbookicon).into(holder.txtBookImage)
    }
}