package com.gm.lollipop.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.localbroadcastmanager.content.LocalBroadcastManager

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
class NetworkStateChangeReceiver : BroadcastReceiver() {

    companion object {
        const val NETWORK_AVAILABLE_ACTION = "com.th.util.network.NetworkStateChangeReceiver"
        const val IS_NETWORK_AVAILABLE = "isNetworkAvailable"

        fun isConnectedToInternet(context: Context?): Boolean {
            return try {
                if (context != null) {
                    val connectivityManager =
                        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val networkCapabilities = connectivityManager.activeNetwork ?: return false
                        val activeNetwork =
                            connectivityManager.getNetworkCapabilities(networkCapabilities)
                                ?: return false

                        return when {
                            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            else -> false
                        }
                    } else {
                        @Suppress("DEPRECATION")
                        return connectivityManager.activeNetworkInfo?.isConnected ?: false
                    }
                }
                false
            } catch (e: Exception) {
                false
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val networkStateIntent = Intent(NETWORK_AVAILABLE_ACTION)
        networkStateIntent.putExtra(IS_NETWORK_AVAILABLE, isConnectedToInternet(context))
        LocalBroadcastManager.getInstance(context!!).sendBroadcast(networkStateIntent)
    }


}