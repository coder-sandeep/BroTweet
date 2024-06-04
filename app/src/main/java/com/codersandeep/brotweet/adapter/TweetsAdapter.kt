package com.codersandeep.brotweet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codersandeep.brotweet.R
import com.codersandeep.brotweet.models.Tweet

class TweetsAdapter(private val tweets: List<Tweet>) :
    RecyclerView.Adapter<TweetsAdapter.TweetsViewHolder>() {

    inner class TweetsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tweetText: TextView = itemView.findViewById(R.id.tvTweetText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.tweet_item, parent, false)
        return TweetsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tweets.size;
    }

    override fun onBindViewHolder(holder: TweetsViewHolder, position: Int) {
        holder.tweetText.text = tweets[position].tweet
    }
}