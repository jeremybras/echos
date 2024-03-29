package fr.echos.articles.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EchosService {

    @GET("everything")
    fun getArticles(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 5,
        @Query("q") query: String? = null,
        @Query("domains") domains: String? = null,
    ): Call<ArticleResponse.Success>
}
