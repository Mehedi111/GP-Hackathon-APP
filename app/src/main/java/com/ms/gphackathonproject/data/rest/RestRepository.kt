package com.ms.gphackathonproject.data.rest

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.gm.lollipop.data.Event
import com.gm.lollipop.data.Resource
import com.gm.lollipop.storage.db.download.OfflineDownloadRepository
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.Constants.DEFAULT_SHORT_BY
import com.ms.gphackathonproject.Constants.DEFAULT_YEAR
import com.ms.gphackathonproject.data.model.DiscoverContent
import com.ms.gphackathonproject.data.model.details.ContentDetails
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
class RestRepository @Inject constructor(
    private val apiService: ApiService,
    private var offlineDownloadRepository: OfflineDownloadRepository,
    private val context: Context
) {

    /**
     * request api for tv shows
     * @param pageNumber for paging
     */
    fun requestTVShowsFromApi(pageNumber: Int): LiveData<Event<Resource<DiscoverContent>>> {
        return LiveDataReactiveStreams.fromPublisher(
            apiService.getTvShows(DEFAULT_YEAR, DEFAULT_SHORT_BY, pageNumber)
                .subscribeOn(Schedulers.io())
                .map {
                    Event(Resource.success(it))
                }
                .onErrorReturn { e ->
                    Event(
                        Resource.error(
                            if (e.message != null) e.message else "", null
                        )
                    )
                }

        )
    }

    /**
     * request api for tv shows
     * @param pageNumber for paging
     */
    fun requestMoviesShowsFromApi(pageNumber: Int): LiveData<Event<Resource<DiscoverContent>>> {
        return LiveDataReactiveStreams.fromPublisher(
            apiService.getMovies(DEFAULT_YEAR, DEFAULT_SHORT_BY, pageNumber)
                .subscribeOn(Schedulers.io())
                .map {
                    Event(Resource.success(it))
                }
                .onErrorReturn { e ->
                    Event(
                        Resource.error(
                            if (e.message != null) e.message else "", null
                        )
                    )
                }

        )
    }

    /*
    * get trending of the week
    * */
    fun getTrendingLiveData(): LiveData<Event<Resource<DiscoverContent>>> {
        return LiveDataReactiveStreams.fromPublisher(
            apiService.getTrending()
                .subscribeOn(Schedulers.io())
                .map {
                    Event(Resource.success(it))
                }
                .onErrorReturn { e ->
                    Event(
                        Resource.error(
                            if (e.message != null) e.message else "", null
                        )
                    )
                }
        )
    }

    /**
     * @param id content id
     * @param type either movie or tv
     *
     */
    fun getContentDetailsLiveData(
        id: Int,
        type: String
    ): LiveData<Event<Resource<ContentDetails>>> {
        return LiveDataReactiveStreams.fromPublisher(
            apiService.getMoviesTvDetails(if (type == Constants.TYPE_TV) "tv" else ("movie"), id)
                .subscribeOn(Schedulers.io())
                .map {
                    Event(Resource.success(it))
                }
                .onErrorReturn { e ->
                    Event(
                        Resource.error(
                            if (e.message != null) e.message else "", null
                        )
                    )
                }
        )
    }


}