package com.example.bookhub.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.R
import com.example.bookhub.activity.DescriptionActivity
import com.example.bookhub.model.Book
import com.squareup.picasso.Picasso


// RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() --> this help to connect the adapter and view holder
class DashboardRecyclerAdapter(val context: Context, val itemList:ArrayList<Book>) : RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {

    // Adapter has view holder in it
    class DashboardViewHolder(view:View): RecyclerView.ViewHolder(view){
        // import R to used it
          val txtBookName: TextView = view.findViewById(R.id.txtBookName)
          val txtBookAuthor: TextView =view.findViewById(R.id.txtBookAuthor)
          val txtBookPrice: TextView =view.findViewById(R.id.txtBookPrice)
          val txtBookRating:TextView =view.findViewById(R.id.BookRating)
          val txtBookImage: ImageView =view.findViewById(R.id.imgBook)
          val llContent:RelativeLayout =view.findViewById(R.id.llContent)
    }

    //this function is responsible for creating initial 10 view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        // from(parent.context) --> given for view holder
        //inflate(R.layout.recycler_dashboard_single_row, parent, false) ---> this for position
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row, parent, false)

        return DashboardViewHolder(view)
    }

    // How many item present
    override fun getItemCount(): Int {
        return itemList.size
    }

    // this help to set the position of items in correct posotion
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {


        val book =itemList[position]
        holder.txtBookName.text = book.bookName
        holder.txtBookAuthor.text = book.bookAuthor
        holder.txtBookPrice.text =book.bookPrice
        holder.txtBookRating.text = book.bookRating

        // image can set using this method only --->" setBackgroundResource(book.bookImage) "
       // holder.txtBookImage.setImageResource(book.bookImage)

        // its generally used to access the image online using
        // " Picasso Library "
        // .error(loaction_file) --> if book not load then this default image show to user
        Picasso.get().load(book.bookImage).error(R.drawable.default_book).into(holder.txtBookImage)

        // it make the content clicked able
        holder.llContent.setOnClickListener{
            //Fragment_Activity -->context
            // Destination source --> DescriptionActivity::class.java
          val intent = Intent(context, DescriptionActivity::class.java)

            // put the bookId to intent
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)

        }
    }
}