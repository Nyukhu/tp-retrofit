package com.example.tp_retrofit.repositories

import androidx.lifecycle.LiveData
import com.example.tp_retrofit.dal.NewsDataSource
import com.example.tp_retrofit.dal.online.ArticleOnlineService
import com.example.tp_retrofit.models.Article

class ArticleRepository {
    private val dataSource: NewsDataSource = ArticleOnlineService()
    fun getArticles(): LiveData<List<Article>> {
        return dataSource.getArticles()
    }

    companion object {
        private var instance: ArticleRepository? = null
        fun getInstance(): ArticleRepository {
            return instance!!
        }
    }
}
