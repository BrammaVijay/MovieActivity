package com.example.newmovieactivityinnavigationgraph.model.retrofit

import com.example.newmovieactivityinnavigationgraph.model.Api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ResponseApi {

    val retrofit: ApiService by lazy {

        Retrofit.Builder()

            .baseUrl("https://api.themoviedb.org/3/")

            .addConverterFactory(GsonConverterFactory.create())

            .build()

            .create(ApiService::class.java)
    }
}