package com.ms.gphackathonproject.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */

@Parcelize
data class Content(
    @SerializedName("backdrop_path") val backdrop_path: String? = null,
    @SerializedName("first_air_date") val first_air_date: String? = null,
    @SerializedName("genre_ids") val genre_ids: List<Int>? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("origin_country") val origin_country: List<String>? = null,
    @SerializedName("original_language") val original_language: String? = null,
    @SerializedName("original_name") val original_name: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("poster_path") val poster_path: String? = null,
    @SerializedName("vote_average") val vote_average: Float? = null,
    @SerializedName("vote_count") val vote_count: Int? = null,
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("original_title") val original_title: String? = null,
    @SerializedName("release_date") val release_date: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("video") val video: Boolean? = null,
    @SerializedName("media_type") val media_type: String? = null
): Parcelable