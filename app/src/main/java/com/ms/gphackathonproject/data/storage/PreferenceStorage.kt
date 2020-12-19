package com.gm.lollipop.data.storage

import android.content.Context
import com.ms.gphackathonproject.Constants

/**
 * @Author: Mehedi Hasan
 * @Date: 12/19/2020
 */
class PreferenceStorage(private val context: Context) : LocalStorage {

    override fun writeMessage(key: String, message: String) {
        context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit().putString(key, message).apply()
    }

    override fun readMessage(key: String): String {
        return context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getString(key, "") ?: ""
    }

    override fun clearPreferences() {

    }

    override fun logout() {
        context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit().clear().apply()
    }

    override fun clearDataByKey(key: String) {
        context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit().remove(key).apply()
    }
}