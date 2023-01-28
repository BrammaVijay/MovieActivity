package com.example.newmovieactivityinnavigationgraph.model.Api

import com.example.newmovieactivityinnavigationgraph.model.Response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{
    @GET("movie/popular")
    suspend fun getPopularApi(@Query("page") page:Int,
                              @Query("api_key")  apiKey: String = "6843ba83dd23a6154bd7d602ef31d431") : Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getTopMovieApi(@Query("page") page:Int,
                               @Query("api_key")  apiKey: String = "6843ba83dd23a6154bd7d602ef31d431") : Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getApiInBottom(@Query("page") page:Int,
                               @Query("api_key")  apiKey: String = "6843ba83dd23a6154bd7d602ef31d431") : Response<MovieResponse>
}
