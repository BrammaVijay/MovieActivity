package com.example.newmovieactivityinnavigationgraph.model.repository

import com.example.newmovieactivityinnavigationgraph.model.Api.ApiService

class MovieRepository(private val serViceApi:ApiService) {

    suspend fun getTopMovie()=serViceApi.getTopMovieApi(1)

    suspend fun getPopularMovie()= serViceApi.getPopularApi(2)

    suspend fun getApiInBottom()=serViceApi.getApiInBottom(3)
}