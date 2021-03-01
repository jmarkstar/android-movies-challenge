package com.jmarkstar.princestheatre.presentation.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmarkstar.princestheatre.databinding.FragmentMovieListItemBinding
import com.jmarkstar.princestheatre.domain.models.MovieModel

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = ArrayList<MovieModel>()

    var onItemClick: ((MovieModel) -> (Unit))? = null

    fun setMovies(items: List<MovieModel>?) {
        movies.clear()
        items?.apply { movies.addAll(items) }
        notifyDataSetChanged()
    }

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = FragmentMovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    inner class MovieViewHolder(private val binding: FragmentMovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(movies[adapterPosition])
            }
        }

        fun bind(movie: MovieModel) = binding.apply {
            moviePoster.setImageURI(movie.poster)
        }
    }
}
