package com.example.newmovieactivityinnavigationgraph.vieModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newmovieactivityinnavigationgraph.model.Response.Movie
import com.example.newmovieactivityinnavigationgraph.model.network.NetWorkResult
import com.example.newmovieactivityinnavigationgraph.model.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(private val repository:MovieRepository):ViewModel() {

    val response=MutableLiveData<NetWorkResult<List<Movie>>>()

    fun getTopMovie(){

        response.value=NetWorkResult.Loading()

        viewModelScope.launch (Dispatchers.Main){

            val result=repository.getTopMovie()

            if (result.isSuccessful){

                val list=result.body()?.movies

                response.value=NetWorkResult.Success(list)

            }
        }

    }

    val responseOne=MutableLiveData<NetWorkResult<List<Movie>>>()

    fun getPopularMovie(){

        responseOne.value= NetWorkResult.Loading()

        viewModelScope.launch (Dispatchers.Main){

            val result=repository.getPopularMovie()

            if (result.isSuccessful){

                val list=result.body()?.movies

                responseOne.value=NetWorkResult.Success(list)

            }
        }
    }

    val responseTwo=MutableLiveData<NetWorkResult<List<Movie>>>()

    fun getApiInBottom(){

        responseTwo.value=NetWorkResult.Loading()

        viewModelScope.launch (Dispatchers.Main){

            val result=repository.getApiInBottom()

            if (result.isSuccessful){

                val list=result.body()?.movies

                responseTwo.value=NetWorkResult.Success(list)
            }
        }
    }
}