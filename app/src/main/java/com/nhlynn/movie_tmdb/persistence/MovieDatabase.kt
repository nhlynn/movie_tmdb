package com.nhlynn.movie_tmdb.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nhlynn.movie_tmdb.data.MovieVO
import com.nhlynn.movie_tmdb.persistence.daos.AppDao

@Database(entities = [MovieVO::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getAppDao(): AppDao

    companion object {
        private var dbINSTANCE: MovieDatabase? = null

        fun getAppDB(context: Context): MovieDatabase {
            if (dbINSTANCE == null) {
                dbINSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "MOVIE_DB"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return dbINSTANCE!!
        }
    }
}