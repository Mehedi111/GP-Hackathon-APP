package com.ms.gphackathonproject.utils.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ms.gphackathonproject.data.model.Content
import java.io.Serializable

/**
 * @Author: Mehedi Hasan
 * @Date: 12/19/2020
 */
class MainContentsTypeConverter : Serializable {
    private val gson = Gson()

    @TypeConverter
    fun stringToContentObject(data: String?): Content {
        if (data == null) {
            return Content()
        }
        val listType = object : TypeToken<Content?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun contentObjectToString(data: Content?): String {
        return gson.toJson(data)
    }
}