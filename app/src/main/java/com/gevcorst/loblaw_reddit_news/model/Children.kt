package com.gevcorst.loblaw_reddit_news.model


import com.squareup.moshi.Json

data class Children(
    val kind: String,
    val data: DataX
)