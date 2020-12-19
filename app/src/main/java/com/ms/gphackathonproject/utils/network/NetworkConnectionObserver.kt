package com.gm.lollipop.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.gm.lollipop.utils.network.NetworkStateChangeReceiver.Companion.IS_NETWORK_AVAILABLE
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Mehedi Hasan on 11/24/2020.
 */
object NetworkConnectionObserver {

    fun isNetworkConnected(context: Context?): Observable<Boolean> {
        val connectionStatus = PublishSubject.create<Boolean>()
        val intentFilter = IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION)
        LocalBroadcastManager.getInstance(context!!).registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false)
                connectionStatus.onNext(isNetworkAvailable)
            }
        }, intentFilter)
        return connectionStatus
    }
}