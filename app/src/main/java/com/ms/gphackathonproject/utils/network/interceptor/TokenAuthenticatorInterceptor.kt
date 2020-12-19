package com.gm.lollipop.utils.network.interceptor

import com.gm.lollipop.data.storage.LocalStorage
import com.ms.gphackathonproject.BuildConfig
import com.ms.gphackathonproject.Constants
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/*
 @Author: Mehedi Hasan
* @Date: 11/25/20
*/
class TokenAuthenticatorInterceptor() : Interceptor {

    private val API_VERSION = "1"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        // Add default headers
        val requestBuilder: Request.Builder = chain.request().newBuilder()

        val api_key = BuildConfig.API_KEY

        requestBuilder.addHeader(Constants.API_KEY_TAG, api_key)
        requestBuilder.cacheControl(CacheControl.Builder().noCache().build())

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}