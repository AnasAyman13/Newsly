package com.news.app

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.news.app.databinding.RandomNewsItemBinding

class RandomNewsAdapter(val newsList:ArrayList<Article>,val a : Activity): RecyclerView.Adapter<RandomNewsAdapter.RandomNewsViewholder>(){

    fun updateData(newArticles: List<Article>) {
        newsList.clear()
        newsList.addAll(newArticles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RandomNewsViewholder {
       return RandomNewsViewholder(RandomNewsItemBinding.inflate(LayoutInflater.from(parent.context)
        ,parent,false))
    }

    override fun onBindViewHolder(
        holder: RandomNewsViewholder,
        position: Int
    ) {
        Glide.with(holder.itemView)
            .load(newsList[position].urlToImage)
            .error(R.drawable.broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.randomImg)

        holder.binding.RandomNewsAcrticleContainer.setOnClickListener {
            val url=newsList[position].url
            val i= Intent(Intent.ACTION_VIEW, url.toUri())
           a.startActivity(i)
        }
    }

    override fun getItemCount(): Int =newsList.size

    class RandomNewsViewholder ( val binding: RandomNewsItemBinding): RecyclerView.ViewHolder(binding.root){

    }

}