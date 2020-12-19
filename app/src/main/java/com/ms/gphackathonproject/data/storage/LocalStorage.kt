package com.gm.lollipop.data.storage

/**
 * @Author: MEHEDI HASAN
 * @Date: 12/19/2020, we
 */
interface LocalStorage {
    fun writeMessage(key: String, message: String)
    fun readMessage(key: String): String
    fun clearPreferences()
    fun logout()
    fun clearDataByKey(key: String)
}