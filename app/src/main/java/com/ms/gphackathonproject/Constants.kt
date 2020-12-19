package com.ms.gphackathonproject

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
object Constants {

    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    const val DATABASE_NAME = "app_db"
    const val HTTP_CACHE_DIR = "okhttp_cache"
    const val HTTP_CACHE_SIZE: Long = 10 * 1024 * 1024 //10MB

    const val HTTP_READ_TIMEOUT = 60L
    const val HTTP_CONNECT_TIMEOUT = 60L

    const val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    const val SHARED_PREFERENCES_NAME = "shared_preferences"
    const val PREFS_KEY_ACCESS_TOKEN = "user_token"

    const val CONTENT_EXTRA = "user_extra"
    const val CONTENT_TYPE = "user_extra_type"

    const val API_KEY_TAG = "api_key"


    const val WISHLIST = 1
    const val NOT_WISHLIST = 0

    //default
    const val DEFAULT_YEAR = 2020
    const val DEFAULT_SHORT_BY = "vote_average.desc"

    // request type
    const val TYPE_MOVIE = "Popular Movies"
    const val TYPE_TV = "TV Series"
    const val TYPE_TRENDING = "Trending Now"
    const val TYPE_WISHLIST = "Wishlist"
}