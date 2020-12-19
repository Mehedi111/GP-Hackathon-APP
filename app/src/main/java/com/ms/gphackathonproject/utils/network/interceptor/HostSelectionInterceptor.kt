package com.gm.lollipop.utils.network.interceptor

import com.ms.gphackathonproject.BuildConfig
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/*
 @Author: Mehedi Hasan
* @Date: 12/19/20
*/
/**
 * This interceptor will replace the destination hostname in the request URL.
 * No parallel asynchronous request will work with this approach
 * Source: https://stackoverflow.com/questions/36498131/set-dynamic-base-url-using-retrofit-2-0-and-dagger-2
 */
class HostSelectionInterceptor : Interceptor {

    @Volatile
    private var host: String? = null
    fun setHost(host: String?) {
        this.host = host
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        if (host != null) {
            // replace the base url without effecting existing paths
            val url = request.url.toString().replace(BuildConfig.BASE_URL, host!!)
            val newUrl = url.toHttpUrlOrNull()
            if (newUrl != null) {
                request = request.newBuilder()
                    .url(newUrl)
                    .build()
            }
        }
        return chain.proceed(request)
    }
}