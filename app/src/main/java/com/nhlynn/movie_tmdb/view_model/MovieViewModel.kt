package com.nhlynn.movie_tmdb.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhlynn.movie_tmdb.data.MovieVO
import com.nhlynn.movie_tmdb.network.POPULAR_MOVIES
import com.nhlynn.movie_tmdb.network.UPCOMING_MOVIES
import com.nhlynn.movie_tmdb.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private var upcomingResponse = MutableLiveData<ArrayList<MovieVO>>()
    val myUpComingResponse: LiveData<ArrayList<MovieVO>> get() = upcomingResponse

    private var popularResponse = MutableLiveData<ArrayList<MovieVO>>()
    val myPopularResponse: LiveData<ArrayList<MovieVO>> get() = popularResponse

    private var errorResponse = MutableLiveData<String>()
    val myErrorResponse: LiveData<String> get() = errorResponse

    init {
        loadData()
    }

    fun loadData(){
        viewModelScope.launch {
            val upComingMovies = launch { getUpComingMovies() }
            val popularMovies = launch { getPopularMovies() }
            upComingMovies.join()
            popularMovies.join()
        }
    }

    private fun getUpComingMovies() {
        viewModelScope.launch {
            try {
                repository.getUpComingMovies().let {
                    if (it.isSuccessful) {
                        upcomingResponse.postValue(it.body()?.results)
                        it.body()?.results?.forEach { movie -> movie.type = UPCOMING_MOVIES }
                        repository.insertMovie(it.body()?.results ?: listOf())
                    } else {
                        errorResponse.postValue(it.message())
                    }
                }
            } catch (e: Exception) {
                if (e.message.toString().lowercase(Locale.ENGLISH)
                        .contains("unable to resolve host")
                ) {
                    val data = repository.getMoviesByType(UPCOMING_MOVIES)
                    if (data.isNotEmpty()) {
                        upcomingResponse.postValue(data as ArrayList<MovieVO>?)
                    }else{
                        errorResponse.postValue("Please check your internet connection")
                    }
                } else {
                    errorResponse.postValue("Something Went Wrong! ${e.message}")
                }
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            try {
                repository.getPopularMovies().let {
                    if (it.isSuccessful) {
                        popularResponse.postValue(it.body()?.results)
                        it.body()?.results?.forEach { movie -> movie.type = POPULAR_MOVIES }
                        repository.insertMovie(it.body()?.results ?: listOf())
                    } else {
                        errorResponse.postValue(it.message())
                    }
                }
            } catch (e: Exception) {
                if (e.message.toString().lowercase(Locale.ENGLISH)
                        .contains("unable to resolve host")
                ) {
                    val data = repository.getMoviesByType(POPULAR_MOVIES)
                    if (data.isNotEmpty()) {
                        popularResponse.postValue(data as ArrayList<MovieVO>)
                    }else{
                        errorResponse.postValue("Please check your internet connection")
                    }
                } else {
                    errorResponse.postValue("Something Went Wrong! ${e.message}")
                }
            }
        }
    }
}