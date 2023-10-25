package com.nhlynn.movie_tmdb.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nhlynn.movie_tmdb.network.ApiService
import com.nhlynn.movie_tmdb.network.BASE_URL
import com.nhlynn.movie_tmdb.persistence.MovieDatabase
import com.nhlynn.movie_tmdb.persistence.daos.AppDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun gSon() = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    fun client(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val origin: Request = chain.request()
                val requestBuilder: Request.Builder = origin.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Accept", "text/plain")
                    .addHeader(
                        "Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiMTRmOWEzMzNhODM2ZmNmZWZmOTM2MGEyNmI0Y2Y0YyIsInN1YiI6IjYyOWM2NTk4MDllZDhmNGFhM2Q4NjQxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.OK3jmggbxUCLtvXpuoHOYIK_Cl0ULoG7Kj1Dq7h9pDE"
                    )
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(gSon: Gson, client: OkHttpClient) =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gSon))
            .build()
            .create(ApiService::class.java)


    @Singleton
    @Provides
    fun getAppDB(context: Application): MovieDatabase {
        return MovieDatabase.getAppDB(context)
    }

    @Singleton
    @Provides
    fun getAppDao(appDB: MovieDatabase): AppDao {
        return appDB.getAppDao()
    }
}