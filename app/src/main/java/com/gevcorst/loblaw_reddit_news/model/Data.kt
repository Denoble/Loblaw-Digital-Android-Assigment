package com.gevcorst.loblaw_reddit_news.model


import com.squareup.moshi.Json

data class Data(
    val modhash: String,
    val dist: Int,
    val children: List<Children>,
    val after: String,
    val before: Any?
)