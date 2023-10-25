package com.nhlynn.movie_tmdb.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhlynn.movie_tmdb.data.MovieVO
import com.nhlynn.movie_tmdb.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private var movieDetailResponse = MutableLiveData<MovieVO>()
    val myMovieDetailResponse: LiveData<MovieVO> get() = movieDetailResponse

    fun addOrRemoveFavourite(id: Int, favourite: Boolean) =
        viewModelScope.launch {
            repository.addOrRemoveFavorite(id, favourite)
        }

    fun getMovieDetail(id: Int) =
        viewModelScope.launch {
            repository.getMovieDetail(id).let {
                movieDetailResponse.postValue(it)
            }
        }
}