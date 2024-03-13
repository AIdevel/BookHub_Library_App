package com.example.bookhub.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.bookhub.R
import com.example.bookhub.adapter.FavouriteRecyclerAdapter
import com.example.bookhub.database.BookDatabase
import com.example.bookhub.database.BookEntity


class FavouritesFragment : Fragment() {

    lateinit var recyclerFavourite: RecyclerView
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavouriteRecyclerAdapter
     var dbBookList = listOf<BookEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_favourites, container, false)

        recyclerFavourite = view.findViewById(R.id.recyclerFavourite)
        progressLayout = view.findViewById(R.id.FavProgressLayout)
        progressBar = view.findViewById(R.id.FavProgressBar)

        // for Recycler view we intilized layoutManager
        // another method -> GridLayoutManager(activity as Context,No_Of_item in a Row)
        layoutManager = GridLayoutManager(activity as Context, 2)

        // Fragment can not access directly Application Fragment
        dbBookList = RetrieveFavourities(activity as Context).execute().get()

        // generally used to hide the data
        if(dbBookList !=null && activity != null){
            progressLayout.visibility =View.GONE
            recyclerAdapter = FavouriteRecyclerAdapter(activity as Context,dbBookList)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManager
        }
        return view
    }

    class RetrieveFavourities(val context: Context): AsyncTask<Void, Void, List<BookEntity>>(){
        override fun doInBackground(vararg params: Void?): List<BookEntity> {

            // Initilized Database
            val db = Room.databaseBuilder(context, BookDatabase::class.java,"book-db").build()

            return db.bookDao().getAllBook()
        }

    }

}