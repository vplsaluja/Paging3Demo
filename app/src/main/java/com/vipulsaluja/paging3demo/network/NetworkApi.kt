package com.vipulsaluja.paging3demo.network

import com.vipulsaluja.paging3demo.data.models.Repo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Vipul Saluja on 14-10-2021.
 */
interface NetworkApi {
    @GET("users/{username}/repos")
    suspend fun fetchRepos(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") size: Int
    ): List<Repo>
}