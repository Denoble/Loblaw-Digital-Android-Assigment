package com.gevcorst.loblaw_reddit_news.ui


import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.gevcorst.loblaw_reddit_news.R
import com.gevcorst.loblaw_reddit_news.databinding.ActivityMainBinding
import com.gevcorst.loblaw_reddit_news.databinding.NewsDetailsBinding
import com.gevcorst.loblaw_reddit_news.viewModel.DetailViewModelFactory
import com.gevcorst.loblaw_reddit_news.viewModel.NewsDetailsViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {
    lateinit var titleText:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = NewsDetailsBinding.inflate(inflater,container,false)
        binding.setLifecycleOwner(this)
        val newsDetails = DetailFragmentArgs.fromBundle(arguments!!).selectedNewsItem
        val viewModelFactory = DetailViewModelFactory(newsDetails,application)
        binding.viewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(NewsDetailsViewModel::class.java)
        titleText = requireNotNull(activity).findViewById(R.id.titleText)
        toolbar.let {
            it?.title = "Re"
        }
        binding.scrollView.visibility = View.VISIBLE
        binding.progressBarPf.visibility = View.GONE

        return binding.root
    }


}
