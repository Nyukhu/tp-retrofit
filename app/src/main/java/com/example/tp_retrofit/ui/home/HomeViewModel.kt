package com.example.tp_retrofit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tp_retrofit.models.Article
import com.example.tp_retrofit.repositories.ArticleRepository
import java.util.concurrent.Executors

class HomeViewModel : ViewModel() {

    private val repository: ArticleRepository = ArticleRepository.getInstance()
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles

    init {
        loadArticles()
    }

    fun loadArticles() {
        Executors.newSingleThreadExecutor().execute() {
            val result = repository.getArticles()
            _articles.value = result.value
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}
