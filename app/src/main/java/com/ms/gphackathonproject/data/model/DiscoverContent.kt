package com.ms.gphackathonproject.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
data class DiscoverContent(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: List<Content>? = null,
    @SerializedName("total_pages") val total_pages: Int? = null,
    @SerializedName("total_results") val total_results: Int? = null,
    var contentType: String? = null // to recognise movies or tv
)