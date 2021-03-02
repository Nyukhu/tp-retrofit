package com.example.tp_retrofit.dal.utils

import com.example.tp_retrofit.dal.online.model.ItemResponse
import com.example.tp_retrofit.dal.online.model.SourceResponse
import com.example.tp_retrofit.models.Article
import com.example.tp_retrofit.models.Source

fun ItemResponse.toArticle() = Article(
    author = author ?: "",
    title = title ?: "",
    description = description ?: "",
    publishedAt = publishedAt ?: "",
    source = source?.toSource() ?: null,
    url = url ?: "",
    urlToImage = urlToImage ?: "",
    content = content ?: ""
)

fun SourceResponse.toSource() = Source(
    id = id ?: "",
    name = name ?: ""
)
