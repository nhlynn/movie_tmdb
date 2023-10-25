package com.nhlynn.movie_tmdb.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nhlynn.movie_tmdb.data.MovieVO
import com.nhlynn.movie_tmdb.databinding.MovieItemBinding
import com.nhlynn.movie_tmdb.delegate.MovieDelegate
import com.nhlynn.movie_tmdb.network.IMAGE_BASE_URL

class MovieViewHolder(
    private val binding: MovieItemBinding,
    private val delegate: MovieDelegate
) : RecyclerView.ViewHolder(binding.root) {
    private var movie = MovieVO()

    init {
        binding.cvMovie.setOnClickListener {
            delegate.onViewMovieDetail(movie)
        }
    }

    fun bindData(movie: MovieVO) {
        this.movie = movie

        binding.tvMovieName.text = movie.title
        binding.tvRating.text = movie.voteAverage.toString()
        Glide.with(binding.ivPoster).load("$IMAGE_BASE_URL/${movie.posterPath}")
            .into(binding.ivPoster)
    }
}