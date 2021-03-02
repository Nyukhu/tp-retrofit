package com.example.tp_retrofit.dal.online

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tp_retrofit.dal.NewsDataSource
import com.example.tp_retrofit.dal.online.services.RetrofitApiService
import com.example.tp_retrofit.dal.utils.toArticle
import com.example.tp_retrofit.models.Article
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleOnlineService : NewsDataSource {
    private val service: RetrofitApiService

    init {
        val retrofit = buildClient()
        // Init the api service with retrofit
        service = retrofit.create(RetrofitApiService::class.java)
    }

    /**
     * Configure retrofit
     */

    private fun buildClient(): Retrofit {
        val httpClient = OkHttpClient.Builder().apply {
            addLogInterceptor(this)
            addApiInterceptor(this)
        }.build()
        return Retrofit
            .Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    /**
     * Add a logger to the client so that we log every request
     */
    private fun addLogInterceptor(builder: OkHttpClient.Builder) {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        builder.addNetworkInterceptor(httpLoggingInterceptor)
    }

    /**
     * Intercept every request to the API and automatically add
     * the api key as query parameter
     */
    private fun addApiInterceptor(builder: OkHttpClient.Builder) {
        builder.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", apiKey)
                    .build()

                val requestBuilder = original.newBuilder()
                    .url(url)
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
    }

    override fun getArticles(): LiveData<List<Article>> {
        val _data = MutableLiveData<List<Article>> ()

        val articleList = service.getArticles().execute().body()?.articles ?: listOf()

        // TODO Convertir la liste des articles du modèle du web service vers le modèle métier ArcicleItem --> Article

        val articles = articleList.map {
            it.toArticle()
        }

        _data.value = articles
        return _data
    }

    companion object {
        private const val apiKey = "9b94cc25989d42f79277f9536abd6bc8"
        private const val apiUrl = "http://newsapi.org/v2"
    }
}
