package com.ms.gphackathonproject.data.model.details

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
class ContentDetails {

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null

    @SerializedName("created_by")
    @Expose
    var createdBy: List<Any>? = null

    @SerializedName("episode_run_time")
    @Expose
    var episodeRunTime: List<Any>? = null

    @SerializedName("first_air_date")
    @Expose
    var firstAirDate: String? = null

    @SerializedName("genres")
    @Expose
    var genres: List<Genre>? = null

    @SerializedName("homepage")
    @Expose
    var homepage: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("in_production")
    @Expose
    var inProduction: Boolean? = null

    @SerializedName("languages")
    @Expose
    var languages: List<String>? = null

    @SerializedName("last_air_date")
    @Expose
    var lastAirDate: String? = null

    @SerializedName("last_episode_to_air")
    @Expose
    var lastEpisodeToAir: LastEpisodeToAir? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("next_episode_to_air")
    @Expose
    var nextEpisodeToAir: Any? = null

    @SerializedName("networks")
    @Expose
    var networks: List<Network>? = null

    @SerializedName("number_of_episodes")
    @Expose
    var numberOfEpisodes: Int? = null

    @SerializedName("number_of_seasons")
    @Expose
    var numberOfSeasons: Int? = null

    @SerializedName("origin_country")
    @Expose
    var originCountry: List<Any>? = null

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null

    @SerializedName("original_name")
    @Expose
    var originalName: String? = null

    @SerializedName("overview")
    @Expose
    var overview: String? = null

    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null

    @SerializedName("production_companies")
    @Expose
    var productionCompanies: List<ProductionCompany>? = null

    @SerializedName("production_countries")
    @Expose
    var productionCountries: List<ProductionCountry>? = null

    @SerializedName("seasons")
    @Expose
    var seasons: List<Season>? = null

    @SerializedName("spoken_languages")
    @Expose
    var spokenLanguages: List<SpokenLanguage>? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("tagline")
    @Expose
    var tagline: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null

    inner class Genre {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null
    }

    inner class LastEpisodeToAir {
        @SerializedName("air_date")
        @Expose
        var airDate: String? = null

        @SerializedName("episode_number")
        @Expose
        var episodeNumber: Int? = null

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("overview")
        @Expose
        var overview: String? = null

        @SerializedName("production_code")
        @Expose
        var productionCode: String? = null

        @SerializedName("season_number")
        @Expose
        var seasonNumber: Int? = null

        @SerializedName("still_path")
        @Expose
        var stillPath: String? = null

        @SerializedName("vote_average")
        @Expose
        var voteAverage: Double? = null

        @SerializedName("vote_count")
        @Expose
        var voteCount: Int? = null
    }

    inner class Network {
        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("logo_path")
        @Expose
        var logoPath: String? = null

        @SerializedName("origin_country")
        @Expose
        var originCountry: String? = null
    }

    inner class ProductionCompany {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("logo_path")
        @Expose
        var logoPath: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("origin_country")
        @Expose
        var originCountry: String? = null
    }

    inner class ProductionCountry {
        @SerializedName("iso_3166_1")
        @Expose
        var iso31661: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null
    }

    inner class Season {
        @SerializedName("air_date")
        @Expose
        var airDate: String? = null

        @SerializedName("episode_count")
        @Expose
        var episodeCount: Int? = null

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("overview")
        @Expose
        var overview: String? = null

        @SerializedName("poster_path")
        @Expose
        var posterPath: String? = null

        @SerializedName("season_number")
        @Expose
        var seasonNumber: Int? = null
    }

    inner class SpokenLanguage {
        @SerializedName("english_name")
        @Expose
        var englishName: String? = null

        @SerializedName("iso_639_1")
        @Expose
        var iso6391: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null
    }
}