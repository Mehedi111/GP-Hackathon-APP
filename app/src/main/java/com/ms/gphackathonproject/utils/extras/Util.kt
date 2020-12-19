package com.gm.lollipop.utils.extras

import android.app.Activity
import android.content.Context
import android.graphics.Point
import com.ms.gphackathonproject.data.model.details.ContentDetails


/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
object Util {
    /**
     * get screen width and height of phone window
     * it'll return width and height as pair
     */
    fun getScreenHeightWidth(context: Context): Pair<Int, Int> {
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y

        return Pair(width, height)
    }

    fun getGenre(genres: List<ContentDetails.Genre>?): String {
        var genre = ""
        if (!genres.isNullOrEmpty()) {
            val sb = StringBuilder()
            for (item in genres) {
                sb.append(" • "+item.name)
            }
            genre = sb.toString()
        }
        return genre
    }


}