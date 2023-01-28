package com.example.newmovieactivityinnavigationgraph.ViewModelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newmovieactivityinnavigationgraph.model.repository.MovieRepository
import com.example.newmovieactivityinnavigationgraph.vieModel.MovieViewModel
import java.lang.IllegalArgumentException

class MovieViewModelFactory constructor(private val repository:MovieRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when (modelClass.canonicalName) {

            MovieViewModel::class.java.canonicalName -> {

                MovieViewModel(this.repository) as T

            }

            else -> {

                throw IllegalArgumentException("ViewModel Not Found")

            }
        }
    }
}