package com.nhlynn.movie_tmdb.repository

import com.nhlynn.movie_tmdb.data.MovieVO
import com.nhlynn.movie_tmdb.network.ApiService
import com.nhlynn.movie_tmdb.persistence.daos.AppDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository
@Inject constructor(private val apiService: ApiService, private val dio: AppDao) {
    suspend fun getUpComingMovies() = apiService.getUpComingMovies()

    suspend fun getPopularMovies() = apiService.getPopularMovies()

    suspend fun getMoviesByType(type: String) = dio.getMovieByType(type = type)

    suspend fun insertMovie(movies: List<MovieVO>) = dio.insertMovie(movies = movies)

    suspend fun addOrRemoveFavorite(id: Int, favorite: Boolean) =
        dio.addOrRemoveFavorite(id, favorite)

    suspend fun getMovieDetail(id: Int) = dio.getMovieDetail(id)
}