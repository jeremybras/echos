package fr.echos.articles.data

import com.google.gson.Gson
import okhttp3.ResponseBody
import javax.inject.Inject

class ErrorParser @Inject constructor() {
    fun parseError(error: ResponseBody?): ArticleResponse.Error {
        return Gson().fromJson(error?.string(), ArticleResponse.Error::class.java)
    }
}
