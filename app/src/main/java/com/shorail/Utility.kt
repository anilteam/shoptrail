package com.shorail

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

object Utility {
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context != null) {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            Log.d("Utility", " NetworkInfo :: info :: $info")
            if (info == null) {
                return false
            } else {
                // Log.d("Utility", " NetworkInfo :: type name :: " + info.getTypeName());
                // Log.d("Utility", " NetworkInfo :: sub-type name :: " +
                // info.getSubtypeName());
                // Log.d("Utility", " NetworkInfo :: state :: " + info.getState());
                // Log.d("Utility", " NetworkInfo :: deatiled state :: " +
                // info.getDetailedState());
                Log.d(
                    "Utility",
                    " NetworkInfo :: info.isConnectedOrConnecting() :: " + info.isConnected
                )
                if (info.isConnected) {
                    return true
                }
            }
        } else {
            return false
        }
        return false
    }
}
