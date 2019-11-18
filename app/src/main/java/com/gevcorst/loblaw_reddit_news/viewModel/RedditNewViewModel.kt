package com.gevcorst.loblaw_reddit_news.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gevcorst.loblaw_reddit_news.api.redditNewsApi
import com.gevcorst.loblaw_reddit_news.model.DetailViewData
import com.gevcorst.loblaw_reddit_news.model.RedditNews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RedditNewViewModel:ViewModel(){

    private val _status = MutableLiveData<String>()
    private val _redditNews = MutableLiveData<RedditNews>()
    private  val _navigateToSelectedNewsItem = MutableLiveData<DetailViewData>()
  val  navigateToSelectedNewsItem: LiveData<DetailViewData>
    get() = _navigateToSelectedNewsItem
    val status: LiveData<String>
        get() = _status
    val redditNews: LiveData<RedditNews>
     get() = _redditNews
    // create a coroutine scope using job that can be cancel at anytime
    private var viewModelJob = Job()
    // run coroutine using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )
    /**
     * Call getRedditNewsProperties() on init so we can display status immediately.
     */
    init {
        getRedditNewsProperties()
    }
    private  fun getRedditNewsProperties(){
        coroutineScope.launch {
            var getNewsDefered = redditNewsApi.retrofitService.getNews()
            try {
                var result = getNewsDefered.await()
                _redditNews.value = result
            }
            catch (e:Exception){
                _status.value ="Failure ${e.localizedMessage}"
            }
        }

    }
    fun displaySelectedNewsItem(item:DetailViewData){
        _navigateToSelectedNewsItem.value = item
    }
    fun displaySelectedNewItemComplete(){
        _navigateToSelectedNewsItem.value = null
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}