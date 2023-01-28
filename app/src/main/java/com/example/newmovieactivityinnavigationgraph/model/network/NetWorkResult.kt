package com.example.newmovieactivityinnavigationgraph.model.network

sealed class NetWorkResult<T>(

    val data: T? = null,

    val error: String? = null

) {
    class Success<T>(data: T?) : NetWorkResult<T>(data = data)

    class Failure<T>(error: String?) : NetWorkResult<T>(error = error)

    class Loading<T>() : NetWorkResult<T>()
}
