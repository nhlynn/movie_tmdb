package com.nhlynn.movie_tmdb.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nhlynn.movie_tmdb.data.MovieVO

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieVO>)

    @Query("SELECT * FROM movie WHERE type = :type")
    suspend fun getMovieByType(type: String): List<MovieVO>

    @Query("UPDATE movie SET favorite = :favourite WHERE movie.id = :id")
    suspend fun addOrRemoveFavorite(id: Int, favourite: Boolean)

    @Query("SELECT * FROM movie WHERE id = :movieId")
    suspend fun getMovieDetail(movieId: Int): MovieVO
}