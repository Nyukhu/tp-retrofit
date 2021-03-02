package com.example.tp_retrofit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tp_retrofit.models.Article
import com.example.tp_retrofit.repositories.ArticleRepository

class HomeViewModel : ViewModel() {

    private val repository: ArticleRepository = ArticleRepository.getInstance()

    fun getArticles(): LiveData<List<Article>> {
        return repository.getArticles()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}
