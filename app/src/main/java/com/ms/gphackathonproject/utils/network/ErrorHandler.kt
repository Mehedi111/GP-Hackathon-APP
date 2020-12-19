package com.gm.lollipop.utils.network

import android.content.Context
import com.gm.lollipop.utils.exception.NoConnectivityException
import com.ms.gphackathonproject.R
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 *
 *
 * General utility functions to parse error message from various throwable and response types
 */
object ErrorHandler {
    private const val TAG = "ErrorHandler"

    /**
     * Returns a readable error message from Throwable object
     *
     * @param context app context
     * @param t       throwable object
     * @return error message in String
     */
    fun getErrorByThrowable(context: Context?, t: Throwable?): String {
        return if (context != null && t != null && t.message != null) {
            when (t) {
                is HttpException -> {
                    // We had non-2XX http error
                    val responseBody = t.response()!!.errorBody()
                    getErrorMessageFromJSONObject(responseBody)
                }
                is SocketTimeoutException -> {
                    // Connection timeout
                    context.resources.getString(R.string.error_timeout)
                }
                is UnknownHostException -> {
                    // Remote host is currently unreachable
                    context.resources.getString(R.string.error_unable_to_reach)
                }
                is NoConnectivityException -> {
                    // A network error happened
                    context.resources.getString(R.string.error_no_internet_connection)
                }
                is IOException -> {
                    // A conversion error happened
                    context.resources.getString(R.string.error_data_conversion)
                }
                else -> {
                    t.message ?: "Server error ! try again"
                }
            }
        } else {
            context?.resources?.getString(R.string.error_unknown) ?: "Server error ! try again"
        }
    }

    /**
     * Parses an error message from JSONObject
     *
     * @param responseBody ResponseBody object
     * @return String value from JSONObject
     */
    private fun getErrorMessageFromJSONObject(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            jsonObject.getString("message")
        } catch (e: Exception) {
            e.message ?: "Internal error !"
        }
    }


}