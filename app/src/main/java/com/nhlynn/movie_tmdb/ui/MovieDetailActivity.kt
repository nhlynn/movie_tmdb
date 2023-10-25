package com.nhlynn.movie_tmdb.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.nhlynn.movie_tmdb.data.MovieVO
import com.nhlynn.movie_tmdb.databinding.ActivityMovieDetailBinding
import com.nhlynn.movie_tmdb.network.IMAGE_BASE_URL
import com.nhlynn.movie_tmdb.utils.MOVIE_ID
import com.nhlynn.movie_tmdb.view_model.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    private val mMovieDetailViewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        renderUI()
    }

    private fun renderUI() {
        val id = intent.getIntExtra(MOVIE_ID, 0)
        mMovieDetailViewModel.getMovieDetail(id)

        waitResponse()
    }

    private fun waitResponse() {
        mMovieDetailViewModel.myMovieDetailResponse.observe(this) {
            showMovieData(it)
        }
    }

    private fun showMovieData(movie: MovieVO?) {
        binding.btnFavourite.isChecked = movie?.favorite == true
        binding.tvMovieName.text = movie?.title
        binding.tvMovieDescription.text = movie?.originalTitle.toString()
        Glide.with(binding.ivPoster).load("$IMAGE_BASE_URL/${movie?.posterPath}")
            .into(binding.ivPoster)

        binding.btnFavourite.setOnCheckedChangeListener { _, isChecked ->
            mMovieDetailViewModel.addOrRemoveFavourite(movie?.id ?: 0, isChecked)
        }
    }

    companion object {
        fun newIntent(context: Context, id: Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).putExtra(MOVIE_ID, id)
        }
    }
}