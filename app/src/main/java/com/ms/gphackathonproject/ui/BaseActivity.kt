package com.ms.gphackathonproject.ui

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.gm.lollipop.storage.db.download.OfflineDaoAccess
import com.gm.lollipop.utils.network.NetworkConnectionObserver
import com.gm.lollipop.utils.network.NetworkStateChangeReceiver
import com.google.android.material.snackbar.Snackbar
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.R
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity() {

    private var networkStateChangeReceiver: NetworkStateChangeReceiver? = null
    protected var disposable: CompositeDisposable? = null

    @IdRes
    protected abstract fun parentLayout(): Int


    @Inject
    lateinit var offlineDaoAccess: OfflineDaoAccess


    open val rxPermissions: RxPermissions by lazy {
        RxPermissions(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        disposable = CompositeDisposable()

        registerNetworkListener()
    }

    private fun registerNetworkListener() {

        // register network state change receiver to detect when network will change
        networkStateChangeReceiver = NetworkStateChangeReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.CONNECTIVITY_ACTION)
        registerReceiver(networkStateChangeReceiver, intentFilter)

        disposable?.add(
            NetworkConnectionObserver.isNetworkConnected(this)
                .subscribe({
                    val connection =
                        if (it) getString(R.string.hint_user_online) else getString(R.string.hint_user_offline)
                    if (!it) {
                        //User in offline, show snackbar
                        showSnackBar(findViewById(parentLayout()), connection)
                    }

                }, {
                    //User in offline, show snackbar
                    showSnackBar(
                        findViewById(parentLayout()), getString(R.string.hint_user_offline)
                    )
                })
        )
    }

    open fun showSnackBar(parentLayout: View?, message: String?) {
        if (parentLayout != null) {
            Snackbar.make(parentLayout, message!!, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.ok)) {}
                .setActionTextColor(Color.WHITE)
                .show()
        }
    }

    open fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    open fun showToastShort(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    open fun moveToActivity(activity: Class<out Activity?>?) {
        startActivity(Intent(this, activity))
        finish()
    }


    private fun disposeAndClear() {
        if (disposable != null) {
            disposable?.clear()
            disposable = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("state_check %s", "onDestroy")
        unregisterReceiver(networkStateChangeReceiver)
        networkStateChangeReceiver = null
        disposeAndClear()

    }
}