package com.gevcorst.loblaw_reddit_news.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gevcorst.loblaw_reddit_news.R
import com.gevcorst.loblaw_reddit_news.databinding.ListNewsItemBinding.inflate
import com.gevcorst.loblaw_reddit_news.model.Children


class RedditNewListAdapter(private val newsProperty: List<Children>,val onClickListener: OnClickListener): RecyclerView.Adapter<RedditNewListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_news_item,parent,false)
        return ViewHolder(inflatedView)
    }


    override fun getItemCount(): Int {
        return newsProperty.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val redditNewsChild = newsProperty.get(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(redditNewsChild)
        }
        holder.bind(newsProperty[position])
    }
    class ViewHolder(v: View):RecyclerView.ViewHolder(v) {

        private var view: View = v
        private val articleTitle:TextView = view.findViewById(R.id.articleTitle)
        private val upVotes:TextView = view.findViewById(R.id.upVotes)
        private val author:TextView = view.findViewById(R.id.author)
        private val url:TextView = view.findViewById(R.id.url)

        fun bind(property:Children){
            articleTitle.text = property.data.title
            upVotes.text = property.data.ups.toString()

            author.text = "Posted by  "  +  property.data.name;
            url.text = property.data.url

        }

    }
    class OnClickListener(val clickListener: (newsChild:Children) -> Unit) {
        fun onClick(newsChild:Children) = clickListener(newsChild)
    }
}


