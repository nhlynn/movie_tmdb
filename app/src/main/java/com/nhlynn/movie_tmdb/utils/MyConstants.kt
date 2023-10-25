package com.nhlynn.movie_tmdb.utils

import java.util.Locale

var screenWidth = 500

var MOVIE_ID="movie_id"

fun parseException(e: Exception): String {
    var error = "Something went wrong!"
    if (e.message?.lowercase(Locale.ENGLISH) != "job was cancelled") {
        error = if (e.message.toString().lowercase(Locale.ENGLISH)
                .contains("unable to resolve host") ||
            e.message.toString().lowercase(Locale.ENGLISH)
                .contains("failed to connect to")
        ) {
            "Something went wrong!"
        } else {
            e.toString()
        }
    }
    return error
}