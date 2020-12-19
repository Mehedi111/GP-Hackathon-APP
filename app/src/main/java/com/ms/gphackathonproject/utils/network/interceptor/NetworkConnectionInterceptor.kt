package com.gm.lollipop.utils.network.interceptor

import android.annotation.SuppressLint
import android.content.Context
import com.gm.lollipop.utils.exception.NoConnectivityException
import com.gm.lollipop.utils.network.NetworkStateChangeReceiver.Companion.isConnectedToInternet
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/*
 @Author: Mehedi Hasan
* @Date: 12/19/20
*/
class NetworkConnectionInterceptor
@SuppressLint("CheckResult")
constructor(private val context: Context) :

    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (isConnectedToInternet(context)) {
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.cacheControl(CacheControl.Builder().noCache().build())
            chain.proceed(requestBuilder.build())
        } else {
            throw NoConnectivityException()
        }
    }
}