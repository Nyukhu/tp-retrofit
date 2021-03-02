package com.example.tp_retrofit.dal

import androidx.lifecycle.LiveData
import com.example.tp_retrofit.models.Article

interface NewsDataSource {
    fun getArticles(): LiveData<List<Article>>
}
