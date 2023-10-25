package com.nhlynn.movie_tmdb.network

import com.nhlynn.movie_tmdb.network.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(UPCOMING_MOVIES)
    suspend fun getUpComingMovies(
        @Query("api_key") apiKey: String = MOVIE_API_KEY,
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET(POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = MOVIE_API_KEY,
        @Query("page") page: Int = 1
    ): Response<MovieResponse>
}