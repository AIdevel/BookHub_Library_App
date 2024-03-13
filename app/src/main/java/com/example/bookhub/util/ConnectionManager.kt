package com.example.bookhub.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
// this function used for establishing the Internet Connection with the App
class ConnectionManager {
    fun checkConnectivity(context: Context) :Boolean{

        // determine which source as Internet
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // determie the current status of device that it has Internet or Not
        // activeNetwork store three value 1--> true (mean internet is Available to Connect)
        // 2--> false (means device is active but it's does not have internet)
        //3--> null (means the device is Inactive or no internet facilities)
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if(activeNetwork?.isConnected !=null){
            return activeNetwork.isConnected
        }else{
            return false
        }
    }
}