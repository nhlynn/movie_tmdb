package com.nhlynn.movie_tmdb.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import com.nhlynn.movie_tmdb.data.MovieVO
import com.nhlynn.movie_tmdb.databinding.MovieItemBinding
import com.nhlynn.movie_tmdb.delegate.MovieDelegate
import com.nhlynn.movie_tmdb.utils.screenWidth
import com.nhlynn.movie_tmdb.view_holder.MovieViewHolder

class MovieAdapter(private val delegate: MovieDelegate) : RecyclerView.Adapter<MovieViewHolder>() {
    private var movieList = arrayListOf<MovieVO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, delegate)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (itemCount > 1) {
            val layoutParams =
                ViewGroup.MarginLayoutParams(
                    screenWidth - 90,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            layoutParams.setMargins(0, 0, 4, 0)
            holder.itemView.layoutParams = layoutParams
        } else {
            val layoutParams =
                ViewGroup.MarginLayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            holder.itemView.layoutParams = layoutParams
        }

        val item = movieList[position]
        holder.bindData(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(movieList: ArrayList<MovieVO>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }
}