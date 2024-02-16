package fr.echos.articles.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EchosService {

    @GET("everything?q=bitcoin")
    fun getArticles(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 5,
        @Query("q") query: String? = null,
        @Query("domains") domains: String? = null,
    ): Call<ArticleResponse.Success>
}
