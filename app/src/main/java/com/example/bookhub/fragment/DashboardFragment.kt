package com.example.bookhub.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.R
import com.example.bookhub.adapter.DashboardRecyclerAdapter
import com.example.bookhub.model.Book
import com.example.bookhub.util.ConnectionManager
import org.json.JSONException


class DashboardFragment : Fragment() {

    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    // Button for checking Internet
   // lateinit var checkInternet:Button


    lateinit var recyclerAdapter: DashboardRecyclerAdapter
    lateinit var progessBar: ProgressBar
    lateinit var progessLayout: RelativeLayout

    val bookInfoList = ArrayList<Book>()


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // view added because it's called in " onCreateView() " function
         recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

       // checkInternet =view.findViewById(R.id.checkInternet)

            progessBar= view.findViewById(R.id.progessBar)
            progessLayout = view.findViewById(R.id.progessLayout)

            // this is used to show the ProgessBar Visible
            progessLayout.visibility = View.VISIBLE


        /*
        checkInternet.setOnClickListener{
            if(ConnectionManager().checkConnectivity(activity as Context)) {
                // Internet is Available

                // Create Dialog Box
                // when connection get it show message -->"Internet Connection Found"
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Internet Connection Found")
                // it's show Positive Message
                dialog.setPositiveButton("OK") { text, listener ->
                    //do nothing

                }
                // it's show Negative Message
                dialog.setNegativeButton("Cancel"){text,listener->
                    // do nothing
                }
                dialog.create()
                dialog.show()


            }else{
                // Internet is Not avialable

                val dialog =AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection is Not Found")
                dialog.setPositiveButton("OK") {text,listener ->
                    //do Nothing
                }

                dialog.setNegativeButton("Cancel") {text,listener ->
                    // Do Nothing
                }
                dialog.create()
                dialog.show()
            }
        }

         */
        // that help to manage the fragment layout
        // i passed "activity" because fragment depend on activity
        layoutManager = LinearLayoutManager(activity)

        // creating server request using JsonObject
        val queue = Volley.newRequestQueue(activity as Context)

        // URL Link
        val url = "http://13.235.250.119/v1/book/fetch_books/"

            if(ConnectionManager().checkConnectivity(activity as Context)){

                // creating JsonObject
                val jsonObjectRequest = object : JsonObjectRequest(
                    Method.GET,
                    url,
                    null,
                    Response.Listener {

                        // Herw we will handle the response
                        try {
                            // this code help to Hide the Progess Layout
                            progessLayout.visibility =View.GONE
                            val success = it.getBoolean("success")

                            if (success) {

                                val data = it.getJSONArray("data")
                                for (i in 0 until data.length()) {
                                    val bookJsonObject = data.getJSONObject(i)
                                    val bookObject = Book(
                                        bookJsonObject.getString("book_id"),
                                        bookJsonObject.getString("name"),
                                        bookJsonObject.getString("author"),
                                        bookJsonObject.getString("rating"),
                                        bookJsonObject.getString("price"),
                                        bookJsonObject.getString("image")
                                    )
                                    bookInfoList.add(bookObject)


                                    // "as" keyword use for type casting
                                    recyclerAdapter =
                                        DashboardRecyclerAdapter(activity as Context, bookInfoList)

                                    // intialised adapter & layoutManager and connect to thier respective place
                                    recyclerDashboard.adapter = recyclerAdapter
                                    recyclerDashboard.layoutManager = layoutManager

/*
                                    //********** provide dashline in app to seperate (Decoration)**************//
                                    recyclerDashboard.addItemDecoration(
                                        DividerItemDecoration(
                                            recyclerDashboard.context,
                                            (layoutManager as LinearLayoutManager).orientation
                                        )
                                    )

 */
                                }
                            } else {
                                Toast.makeText(
                                    activity as Context,
                                    "Some Error Occurred",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: JSONException){
                            Toast.makeText(activity as Context,"Some unexpected error occurred!!!",Toast.LENGTH_SHORT).show()
                        }

                    }, Response.ErrorListener {

                        if(activity != null) {
                            //Here we will handle the errors
                            Toast.makeText(
                                activity as Context,
                                "Volley error Occurred!!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }}) {

                    // This Function used to sending the Header content to API
                    override fun getHeaders(): MutableMap<String, String> {
                        // HashMap == MutableMap
                        val headers = HashMap<String,String>()

                        // Date we send and Receive from API is in the Form of "json"
                        headers["Content-type"] = "application/json"


                        headers["token"] = "9bf534118365f1"
                        return headers
                    }

                }
                queue.add(jsonObjectRequest)
            }else{
                // Internet is Not avialable

                val dialog =AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection is Not Found")
                dialog.setPositiveButton("Open Setting") {text,listener ->
                    val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingIntent)
                    activity?.finish() // help to Refresh the app
                }

                dialog.setNegativeButton("Exit App") {text,listener ->

                    // this help to close the app directly at any State
                    ActivityCompat.finishAffinity(activity as Activity)
                }
                dialog.create()
                dialog.show()

            }

        return view
    }

}