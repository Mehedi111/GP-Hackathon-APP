package com.ms.gphackathonproject.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.gm.lollipop.data.Event
import com.gm.lollipop.data.Resource
import com.gm.lollipop.storage.db.download.OfflineContent
import com.gm.lollipop.storage.db.download.OfflineContentRepository
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.data.model.Content
import com.ms.gphackathonproject.data.model.DiscoverContent
import com.ms.gphackathonproject.data.model.details.ContentDetails
import com.ms.gphackathonproject.data.rest.RestRepository

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
/*
* EVENT CLASS TO PREVENT SEND THE ALREADY PASSED VALUES TO ACTIVITY
* RESOURCE DATA HOLD OBJECT AND PASS AGIAN WHILE ACTIVITY RESTART OR CONFIGURATION CHANGE
* EVENT WILL SEND ONLY ONE TIME
* */

class MainViewModel @ViewModelInject constructor(
    private val restRepository: RestRepository,
    private val offlineContentRepository: OfflineContentRepository
) : ViewModel() {

    private val _moviesLiveData: MediatorLiveData<Event<Resource<DiscoverContent>>> by lazy {
        MediatorLiveData<Event<Resource<DiscoverContent>>>()
    }

    private val _tvLiveData: MediatorLiveData<Event<Resource<DiscoverContent>>> by lazy {
        MediatorLiveData<Event<Resource<DiscoverContent>>>()
    }

    private val _trendingLiveData: MediatorLiveData<Event<Resource<DiscoverContent>>> by lazy {
        MediatorLiveData<Event<Resource<DiscoverContent>>>()
    }

    private val _offlineMoviesLiveData: MediatorLiveData<Event<Resource<DiscoverContent>>> by lazy {
        MediatorLiveData<Event<Resource<DiscoverContent>>>()
    }

    private val _offlineTvLiveData: MediatorLiveData<Event<Resource<DiscoverContent>>> by lazy {
        MediatorLiveData<Event<Resource<DiscoverContent>>>()
    }

    private val _offlineTrendingLiveData: MediatorLiveData<Event<Resource<DiscoverContent>>> by lazy {
        MediatorLiveData<Event<Resource<DiscoverContent>>>()
    }

    private val _contentDetailsLiveData: MediatorLiveData<Event<Resource<ContentDetails>>> by lazy {
        MediatorLiveData<Event<Resource<ContentDetails>>>()
    }

    private val _offlineDownloadsLiveData: MediatorLiveData<Resource<List<OfflineContent>>> =
        MediatorLiveData<Resource<List<OfflineContent>>>()


    /*
     * observe live data of movies and tv
     * */
    fun observeMoviesLiveData(): LiveData<Event<Resource<DiscoverContent>>> {
        return _moviesLiveData
    }

    fun observeTVLiveData(): LiveData<Event<Resource<DiscoverContent>>> {
        return _tvLiveData
    }

    /*
    * Observer trending of the week
    * */
    fun observeTrendingLiveData(): LiveData<Event<Resource<DiscoverContent>>> {
        return _trendingLiveData
    }

    /**
     * @param type movie or tv
     */

    fun getTvShows(pageNumber: Int) {
        _tvLiveData.postValue(Event(Resource.loading(null)))
        val resourceLiveData = restRepository.requestTVShowsFromApi(pageNumber)
        _tvLiveData.addSource(resourceLiveData) { data ->
            if (data != null) {
                _tvLiveData.value = data
            }
            _tvLiveData.removeSource(resourceLiveData)
        }
    }

    fun getMovies(pageNumber: Int) {
        _moviesLiveData.postValue(Event(Resource.loading(null)))
        val resourceLiveData = restRepository.requestMoviesShowsFromApi(pageNumber)
        _moviesLiveData.addSource(resourceLiveData) { data ->
            if (data != null) {
                _moviesLiveData.value = data
            }
            _moviesLiveData.removeSource(resourceLiveData)
        }
    }

    /*
    * get trending of the week
    * */
    fun getTrendingLiveData() {
        _trendingLiveData.postValue(Event(Resource.loading(null)))
        val resourceLiveData = restRepository.getTrendingLiveData()
        _trendingLiveData.addSource(resourceLiveData) { data ->
            if (data != null) {
                _trendingLiveData.value = data
            }
            _trendingLiveData.removeSource(resourceLiveData)
        }
    }


    /*
   * Observer content details live data
   * */
    fun observeContentDetailsLiveData(): LiveData<Event<Resource<ContentDetails>>> {
        return _contentDetailsLiveData
    }

    /**
     * get content details
     * @param id content id
     * @param type type of content either movie or tv shows
     */
    fun getContentDetailsLiveData(id: Int, type: String) {
        _contentDetailsLiveData.postValue(Event(Resource.loading(null)))
        val resourceLiveData = restRepository.getContentDetailsLiveData(id, type)
        _contentDetailsLiveData.addSource(resourceLiveData) { data ->
            if (data != null) {
                _contentDetailsLiveData.value = data
            }
            _contentDetailsLiveData.removeSource(resourceLiveData)
        }
    }

    fun getOfflineMovies(){
        _offlineMoviesLiveData.postValue(Event(Resource.loading(null)))
        val resourceLiveData = restRepository.getHomeDataFromCache(Constants.TYPE_MOVIE)
        _offlineMoviesLiveData.addSource(resourceLiveData) { data ->
            if (data != null) {
                _offlineMoviesLiveData.value = data
            }
            _offlineMoviesLiveData.removeSource(resourceLiveData)
        }
    }

    fun getOfflineTVShows(){
        _offlineTvLiveData.postValue(Event(Resource.loading(null)))
        val resourceLiveData = restRepository.getHomeDataFromCache(Constants.TYPE_TV)
        _offlineTvLiveData.addSource(resourceLiveData) { data ->
            if (data != null) {
                _offlineTvLiveData.value = data
            }
            _offlineTvLiveData.removeSource(resourceLiveData)
        }
    }

    fun getOfflineTrending(){
        _offlineTrendingLiveData.postValue(Event(Resource.loading(null)))
        val resourceLiveData = restRepository.getHomeDataFromCache(Constants.TYPE_TRENDING)
        _offlineTrendingLiveData.addSource(resourceLiveData) { data ->
            if (data != null) {
                _offlineTrendingLiveData.value = data
            }
            _offlineTrendingLiveData.removeSource(resourceLiveData)
        }
    }
    /*
   * Observer data from local
   * */
    fun observeOfflineMovieLiveData(): LiveData<Event<Resource<DiscoverContent>>> {
        return _offlineMoviesLiveData
    }
    fun observeOfflineTVLiveData(): LiveData<Event<Resource<DiscoverContent>>> {
        return _offlineTvLiveData
    }

    fun observeOfflineTrendingLiveData(): LiveData<Event<Resource<DiscoverContent>>> {
        return _offlineTrendingLiveData
    }




    /*
    * change wishlist status
    * */
    fun changeWishListStatus(makeWish: Boolean, content: Content) {
        offlineContentRepository.changeWishListStatus(makeWish, content)
    }


    /*
    * get all wish list from local db
    * */
    fun observeOfflineLiveData(): LiveData<Resource<List<OfflineContent>>> {
        return _offlineDownloadsLiveData
    }

    fun getWishlistLiveData() {
        _offlineDownloadsLiveData.postValue(Resource.loading(null))
        val resourceLiveData = offlineContentRepository.getAllWishList()
        _offlineDownloadsLiveData.addSource(resourceLiveData) { data ->
            if (data != null) {
                _offlineDownloadsLiveData.value = data
            }
            _offlineDownloadsLiveData.removeSource(resourceLiveData)
        }
    }

}