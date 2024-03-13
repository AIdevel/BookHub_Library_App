package com.example.bookhub.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.privacysandbox.tools.core.model.Method
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.R
import com.example.bookhub.database.BookDatabase
import com.example.bookhub.database.BookEntity
import com.example.bookhub.model.Book
import com.example.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {

    lateinit var bookName : TextView
    lateinit var bookAuthor : TextView
    lateinit var bookPrice : TextView
    lateinit var bookImage : ImageView
    lateinit var bookRating : TextView
    lateinit var bookDescription :TextView
    lateinit var bookAddFav : Button
    lateinit var progessLayout: RelativeLayout
    lateinit var progessBar: ProgressBar
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    var bookId:String? = "100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        bookName = findViewById(R.id.txtBName)
        bookAuthor = findViewById(R.id.txtBAuthor)
        bookPrice = findViewById(R.id.txtBPrice)
        bookImage = findViewById(R.id.BookImg)
        bookRating = findViewById(R.id.rating)
        bookDescription = findViewById(R.id.description)
        bookAddFav = findViewById(R.id.AddToFav)

        // to show in app directly
        progessLayout = findViewById(R.id.BProgressLayout)
        progessLayout.visibility = View.VISIBLE
        progessBar = findViewById(R.id.BProgressBar)
        progessBar.visibility = View.VISIBLE

        toolbar =findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Details"

        if (intent != null) {
            bookId = intent.getStringExtra("book_id")
        } else {
            // it's used to end all the program that currently running
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some unexpected error occurred!",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (bookId == "100") {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some unexpected error occurred!",
                Toast.LENGTH_SHORT
            ).show()
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"

        if(ConnectionManager().checkConnectivity(this@DescriptionActivity)){

            val jsonParams = JSONObject()
            jsonParams.put("book_id", bookId)

            val jsonRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {
                try {
                    val success = it.getBoolean("success")
                    if (success) {
                        val bookJsonObject = it.getJSONObject("book_data")
                        progessLayout.visibility = View.GONE


                        // .error(loaction of Default_Image).into(img variable that Declare in lateinit)
                        val bookImageUrl = bookJsonObject.getString("image")
                        Picasso.get().load(bookJsonObject.getString("image")).error(R.drawable.default_book).into(bookImage)
                        bookName.text = bookJsonObject.getString("name")
                        bookAuthor.text = bookJsonObject.getString("author")
                        bookPrice.text = bookJsonObject.getString("price")
                        bookRating.text = bookJsonObject.getString("rating")
                        bookDescription.text = bookJsonObject.getString("description")

                        val bookEntity = BookEntity(
                            bookId?.toInt() as Int,
                            bookName.text.toString(),
                            bookAuthor.text.toString(),
                            bookPrice.text.toString(),
                            bookRating.text.toString(),
                            bookDescription.text.toString(),
                            bookImageUrl

                        )

                        val checkFav = DBAsyncTask(applicationContext,bookEntity,1).execute()
                        val isFav = checkFav.get()

                        if(isFav){
                            bookAddFav.text ="Remove from Favourites"
                            val favColor = ContextCompat.getColor(applicationContext,R.color.purple_200)
                            bookAddFav.setBackgroundColor(favColor)
                        }else{
                            bookAddFav.text = "Add to Favourites"
                            val noFavColor = ContextCompat.getColor(applicationContext,R.color.teal_200)
                             bookAddFav.setBackgroundColor(noFavColor)
                        }
                        bookAddFav.setOnClickListener {
                            if (!DBAsyncTask(applicationContext, bookEntity, 1).execute().get()) {

                                val async = DBAsyncTask(applicationContext, bookEntity, 2).execute()
                                val result = async.get()
                                if (result) {
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Book added to Favourites",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                bookAddFav.text = "Remove from Favourites"
                                val favColor =
                                    ContextCompat.getColor(applicationContext, R.color.purple_200)
                                bookAddFav.setBackgroundColor(favColor)
                            } else {
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Some Error Occurred!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                        }else{

                            val async = DBAsyncTask(applicationContext,bookEntity,3).execute()
                               val result =async.get()

                               if(result){
                                   Toast.makeText(this@DescriptionActivity,"Book removed from favourite",Toast.LENGTH_SHORT).show()

                                   bookAddFav.text ="Add to Favourities"
                                   val noFavColor = ContextCompat.getColor(applicationContext,R.color.teal_200)
                                   bookAddFav.setBackgroundColor(noFavColor)
                               } else{
                                   Toast.makeText(this@DescriptionActivity,"Some Error Occured!",Toast.LENGTH_SHORT).show()

                               }
                        }
                    }

                } else {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Some Error Occured!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(
                        this@DescriptionActivity,
                        "Some error occurred!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }, Response.ErrorListener {
                Toast.makeText(this@DescriptionActivity, "Volley Error $it", Toast.LENGTH_SHORT)
                    .show()

            }){

                // This Function used to sending the Header content to API
                override fun getHeaders(): MutableMap<String, String> {
                    // HashMap == MutableMap
                    val headers = HashMap<String,String>()

                    // Date we send and Receive from API is in the Form of "json"
                    headers["Content-type"] = "application/json"


                    headers["token"]= "072535544bd716"
                    return headers
                }

            }
            // this line is complusary to ecxcute the POST Method otherwise the Data will not fetch by the API
            queue.add(jsonRequest)
        }
        else {
            val dialog = AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                //open setting
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                finish()
            }

            dialog.setNegativeButton("Exit"){text, listener ->
                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }
            dialog.create()
            dialog.show()
        }

        }


    class DBAsyncTask(val context: Context, val bookEntity: BookEntity,val mode:Int): AsyncTask<Void,Void,Boolean>() {

       /*
       mode 1 -> Check DB if the book is favourite or not
       mode 2 -> Save the book into DB as favourite
       mode 3 -> Remove the favourite book
        */
        // open the DataBase
        val db = Room.databaseBuilder(context,BookDatabase::class.java, "book_db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when(mode){

                1 -> {
                    // Check DB if the book is favourite or not

                    // it's will give result when the book Id & Book Entity present in Database other wise give NULL.
                    val book: BookEntity? = db.bookDao().getBookById(bookEntity.book_id.toString())
                    // CLose the DataBase otherwise it take unnecessary memory
                    db.close()

                    // It's check when the return value of book is null then it return false otherwise give true
                    return book !=null
                }

                2 -> {
                    // save the book into DB as favourite
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true

                }

                3 -> {
                    // remove the book from Favourite
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true

                }


        }
           return false

    }

    }

    }
