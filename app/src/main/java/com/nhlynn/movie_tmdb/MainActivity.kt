package com.nhlynn.movie_tmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nhlynn.movie_tmdb.adapter.MovieAdapter
import com.nhlynn.movie_tmdb.data.MovieVO
import com.nhlynn.movie_tmdb.databinding.ActivityMainBinding
import com.nhlynn.movie_tmdb.delegate.MovieDelegate
import com.nhlynn.movie_tmdb.ui.MovieDetailActivity
import com.nhlynn.movie_tmdb.utils.screenWidth
import com.nhlynn.movie_tmdb.view_model.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MovieDelegate {
    private lateinit var binding: ActivityMainBinding

    private val mMovieViewModel: MovieViewModel by viewModels()

    private lateinit var mUpComingAdapter: MovieAdapter
    private lateinit var mPopularAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        renderUI()
    }

    private fun renderUI() {
        val resources = this.resources
        screenWidth = resources.displayMetrics.widthPixels

        setUpAdapter()
        waitResponse()

        binding.btnRetry.setOnClickListener {
            binding.pbLoading.visibility = View.VISIBLE
            mMovieViewModel.loadData()
        }
    }

    private fun setUpAdapter() {
        mUpComingAdapter = MovieAdapter(this)
        binding.rvUpcomingMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpcomingMovies.setHasFixedSize(true)
        binding.rvUpcomingMovies.adapter = mUpComingAdapter

        mPopularAdapter = MovieAdapter(this)
        binding.rvPopularMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopularMovies.setHasFixedSize(true)
        binding.rvPopularMovies.adapter = mPopularAdapter
    }

    private fun waitResponse() {
        mMovieViewModel.myUpComingResponse.observe(this) {
            binding.pbLoading.visibility = View.GONE
            binding.btnRetry.visibility = View.GONE
            if (it.isNullOrEmpty()) {
                binding.lblUpcomingEmpty.visibility = View.VISIBLE
                binding.rvUpcomingMovies.visibility = View.GONE
            } else {
                binding.lblUpcomingEmpty.visibility = View.GONE
                binding.rvUpcomingMovies.visibility = View.VISIBLE
                mUpComingAdapter.setData(it)
            }
        }

        mMovieViewModel.myPopularResponse.observe(this) {
            binding.pbLoading.visibility = View.GONE
            binding.btnRetry.visibility = View.GONE
            if (it.isNullOrEmpty()) {
                binding.lblPopularEmpty.visibility = View.VISIBLE
                binding.rvUpcomingMovies.visibility = View.GONE
            } else {
                binding.lblPopularEmpty.visibility = View.GONE
                binding.rvUpcomingMovies.visibility = View.VISIBLE
                mPopularAdapter.setData(it)
            }
        }

        mMovieViewModel.myErrorResponse.observe(this) {
            binding.pbLoading.visibility = View.GONE
            binding.btnRetry.visibility = View.VISIBLE
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewMovieDetail(movie: MovieVO) {
        startActivity(
            MovieDetailActivity.newIntent(this, movie.id ?: 0)
        )
    }
}