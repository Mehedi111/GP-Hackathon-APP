package com.gm.lollipop.utils.extras

import com.ms.gphackathonproject.Constants

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */

object CharParser {

    /**
     * Replaces <...> tag inside the url with a image size
     */
    fun getImageUrlFromPath(path: String, type: String): String {
        return if (path.equals(null) || path.isEmpty()) {
            ""
        } else {
            when (type) {
                Constants.TYPE_MOVIE -> Constants.IMAGE_BASE_URL + "w342" + path
                Constants.TYPE_TV -> Constants.IMAGE_BASE_URL + "w342" + path
                Constants.TYPE_TRENDING -> Constants.IMAGE_BASE_URL + "w342" + path
                else -> {
                    Constants.IMAGE_BASE_URL + "w342" + path
                }
            }
        }
    }


}