package com.gevcorst.loblaw_reddit_news.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.gevcorst.loblaw_reddit_news.model.DataX
import com.gevcorst.loblaw_reddit_news.model.DetailViewData


class NewsDetailsViewModel(newsItem:DetailViewData, app:Application) :AndroidViewModel(app){
    private val _selectedNews = MutableLiveData<DetailViewData>()
    val selectedNews: LiveData<DetailViewData>
     get() = _selectedNews
    init {
        _selectedNews.value = newsItem
    }

}
class DetailViewModelFactory(
    private val newsDetails:DetailViewData,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsDetailsViewModel::class.java)) {
            return NewsDetailsViewModel(newsDetails, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}