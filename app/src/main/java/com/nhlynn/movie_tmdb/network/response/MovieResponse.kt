package com.nhlynn.movie_tmdb.network.response

import com.google.gson.annotations.SerializedName
import com.nhlynn.movie_tmdb.data.MovieVO

class MovieResponse {
    @SerializedName("page")
    val page: Int? = null

    @SerializedName("results")
    val results: ArrayList<MovieVO>? = null

    var error: String? = null
}