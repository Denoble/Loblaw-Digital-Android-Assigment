package com.gevcorst.loblaw_reddit_news.model


import com.squareup.moshi.Json

data class RedditNews(
    val kind: String,
    val `data`: Data
)