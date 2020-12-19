package com.ms.gphackathonproject.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
data class DiscoverContent(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Content>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int,
    var contentType: String? = null // to recognise movies or tv
)