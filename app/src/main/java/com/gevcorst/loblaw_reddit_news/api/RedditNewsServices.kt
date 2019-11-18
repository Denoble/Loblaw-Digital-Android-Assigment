package com.gevcorst.loblaw_reddit_news.api

import com.gevcorst.loblaw_reddit_news.model.RedditNews
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.reddit.com/"
//Sett up Moshi to be used by retrofit
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
//Set up retrofit with the Base URL
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()
interface RedditNewsApiService{
    @GET("r/kotlin/.json")
    fun getNews(): Deferred<RedditNews>

}
object redditNewsApi{
    val retrofitService:RedditNewsApiService by lazy {
        retrofit.create(RedditNewsApiService::class.java)
    }
}