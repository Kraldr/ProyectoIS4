package com.example.primera

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/todos")

    fun fecthAllUsers(): Call<List<allUsers>>
}