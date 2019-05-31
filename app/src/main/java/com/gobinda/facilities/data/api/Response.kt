package com.gobinda.facilities.data.api

import com.gobinda.facilities.data.model.News

data class NewsResponse(
    val status : String?,
    val numResults: Int?,
    val results : List<News>?
)
