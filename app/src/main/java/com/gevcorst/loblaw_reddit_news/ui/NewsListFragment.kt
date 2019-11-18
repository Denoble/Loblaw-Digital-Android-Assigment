package com.gevcorst.loblaw_reddit_news.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gevcorst.loblaw_reddit_news.R
import com.gevcorst.loblaw_reddit_news.databinding.NewsListFragmentBinding
import com.gevcorst.loblaw_reddit_news.model.DetailViewData
import com.gevcorst.loblaw_reddit_news.viewModel.RedditNewViewModel

/**
 * A simple [Fragment] subclass.
 */
class NewsListFragment : Fragment() {
    private val viewModel:RedditNewViewModel by lazy {
        ViewModelProviders.of(this).get(RedditNewViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding =  NewsListFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this;
         viewModel.redditNews.observe(this, Observer {
             Log.i("NEWLIST",it.toString());
             binding.newsList.layoutManager = LinearLayoutManager(this.requireContext())
             binding.newsList.adapter = RedditNewListAdapter(it.data.children,RedditNewListAdapter.OnClickListener{
                 val dataDetail = DetailViewData(it.data.title,it.data.author,
                     it.data.ups.toString(),it.data.url,it.data.selftext)
                  viewModel.displaySelectedNewsItem(dataDetail)
             })
             binding.progressBarPf .visibility = View.GONE
             binding.newsList.visibility = View.VISIBLE
         })
        viewModel.navigateToSelectedNewsItem.observe(this, Observer {
            if(null != it){
                this.findNavController().navigate(NewsListFragmentDirections.actionNewsListFragmentToDetailFragment(it))
                viewModel.displaySelectedNewItemComplete()
            }
        })
        return binding.root
    }


}
