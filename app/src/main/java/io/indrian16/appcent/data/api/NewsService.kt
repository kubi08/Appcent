package io.indrian16.appcent.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("games")
    fun getTopHeadlines(@Query("page") page: Int): Single<GetNewsApiResponse>

    @GET("games/detail")
    fun getEverything(@Query("") query: String): Single<GetNewsApiResponse>
}