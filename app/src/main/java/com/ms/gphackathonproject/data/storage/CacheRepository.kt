package com.ms.gphackathonproject.data.storage

import com.gm.lollipop.data.Event
import com.gm.lollipop.data.Resource
import com.gm.lollipop.data.storage.LocalStorage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ms.gphackathonproject.data.model.Content
import com.ms.gphackathonproject.data.model.DiscoverContent
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
class CacheRepository @Inject constructor(
    private val gson: Gson,
    private val localStorage: LocalStorage
) {
    fun storeHomeData(key: String, discoverContent: DiscoverContent) {
        Timber.d("check_item %s", key)
        localStorage.writeMessage(key, gson.toJson(discoverContent))
    }

    private fun getHomeData(key: String): DiscoverContent {
        return getHomeObjectFromString(localStorage.readMessage(key))
    }

    private fun getHomeObjectFromString(data: String?): DiscoverContent {
        val collectionType = object : TypeToken<DiscoverContent>() {}.type
        return gson.fromJson(data, collectionType) ?: DiscoverContent()
    }

    fun retrieveDiscoverPageData(key: String): Observable<Event<Resource<DiscoverContent>>> {
        return Observable.fromCallable {
            val obj = getHomeData(key)
            if (obj.results != null) {
                Event(Resource.success(obj))
            } else {
                null
            }
        }
    }

    fun clearDataByKey(key: String) {
        localStorage.clearDataByKey(key)
    }

}