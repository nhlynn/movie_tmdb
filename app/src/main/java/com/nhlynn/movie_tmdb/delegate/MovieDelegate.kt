package com.nhlynn.movie_tmdb.delegate

import com.nhlynn.movie_tmdb.data.MovieVO

interface MovieDelegate {
    fun onViewMovieDetail(movie: MovieVO)
}