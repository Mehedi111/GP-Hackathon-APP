package com.ms.gphackathonproject.data.rest

import com.ms.gphackathonproject.BuildConfig
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.data.model.DiscoverContent
import com.ms.gphackathonproject.data.model.details.ContentDetails
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
interface ApiService {

    @GET("discover/tv?")
    fun getTvShows(
        @Query("primary_release_year") year: Int,
        @Query("sort_by") sort_by: String,
        @Query("page") page: Int,
        @Query(Constants.API_KEY_TAG) api_key: String = BuildConfig.API_KEY,
    ): Flowable<DiscoverContent>

    @GET("discover/movie?")
    fun getMovies(
        @Query("primary_release_year") year: Int,
        @Query("sort_by") sort_by: String,
        @Query("page") page: Int,
        @Query(Constants.API_KEY_TAG) api_key: String = BuildConfig.API_KEY
    ): Flowable<DiscoverContent>


    @GET("trending/all/week?")
    fun getTrending(
        @Query(Constants.API_KEY_TAG) api_key: String = BuildConfig.API_KEY
    ): Flowable<DiscoverContent>


    @GET("{type}/{id}?")
    fun getMoviesTvDetails(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query(Constants.API_KEY_TAG) api_key: String = BuildConfig.API_KEY
    ): Flowable<ContentDetails>

}