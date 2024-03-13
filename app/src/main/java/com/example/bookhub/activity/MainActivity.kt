package com.example.bookhub.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.bookhub.fragment.AboutFragment
import com.example.bookhub.fragment.DashboardFragment
import com.example.bookhub.fragment.FavouritesFragment
import com.example.bookhub.fragment.ProfileFragment
import com.example.bookhub.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout

    // used this for appcompact --> "androidx.appcompat.widget.Toolbar"

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

     var previousMenuItem: MenuItem? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // R.id." Id name of tag " keep in mind

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)
        setUpToolbar()




        // help to set the first Screen in app
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, DashboardFragment())
            .addToBackStack("Dashboard")
            .commit()
        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)




        // this Function help to --> to activate the humberger icon button
        //  and make the Humbeger functional
    val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,
        R.string.open_drawer,
        R.string.closed_drawer
    )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        // " actionBarDrawerToggle.syncState() " -->  This help to convert the Humberger icon to arrow and vice versa
        /* --> This is performed when click on Humberger icon slide come up and
               arrow display at the position of Humberger
        -->    But when click on outer position of slide then arrow convert to Humberger Icon
         */
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem!=null){
                // it make the   previousMenuItem become false
                previousMenuItem?.isCheckable = false
            }
            // it make the current item become true
            it.isCheckable = true
            it.isChecked = true
            //current value assign to previousMenuItem
            previousMenuItem = it

            when(it.itemId) {
                R.id.dashboard -> {

                    // start Fragment to work
                    supportFragmentManager.beginTransaction()

                        // it help to replace blank page
                        .replace(R.id.frame, DashboardFragment())

                        // to activate BackStack --->
                        // that help to provide reverse activity/ fragment without closing app
                        //.addToBackStack("Dashboard")

                        // to Apply Transaction
                        .commit()

                    // that give the Title when visit to Dashboard Fragment
                    supportActionBar?.title ="Dashboard"

                    drawerLayout.closeDrawers()

                }
                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavouritesFragment())
                        //.addToBackStack("Favouities")
                        .commit()
                    supportActionBar?.title = "Favourities"
                    drawerLayout.closeDrawers()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())
                       // .addToBackStack("Profile")
                        .commit()
                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.about -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, AboutFragment())
                       // .addToBackStack("About")
                        .commit()
                    supportActionBar?.title = "About App"
                    drawerLayout.closeDrawers()

                }
            }
            // passed the value of boolean as true by using --> " @setNavigationItemSelectedListener" otherwise give Error
            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        // help to passed title to navigation bar  & ? for data will passed later
        supportActionBar?.title = "Toolbar Title"

        // to enable the "HOME" button & passed true otherwise not get activated
        supportActionBar?.setHomeButtonEnabled(true)

        // to enable to display "Home" Button

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
// it's make the Home Button functional
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //Extracting the id of variable and store in id
        val id = item.itemId

    // android.R.id.home(we write this because Humberger icon present at Home) = Humberger icon
        if(id== android.R.id.home){
            // this help to make open the slide from left side in app
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
// this function have to provide on back click functionality
    override fun onBackPressed() {
        //.findFragmentById(R.id.frame) -->help to find the fragment and assign to frag
        val frag =supportFragmentManager.findFragmentById(R.id.frame)

        when(frag){
            !is DashboardFragment -> supportFragment()

            else -> super.onBackPressed()
        }
    }

    fun supportFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, DashboardFragment())

            .commit()

        supportActionBar?.title ="Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }
}