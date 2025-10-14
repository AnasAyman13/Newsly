package com.news.app

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast

import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.news.app.databinding.ArticleListItemBinding

class NewsAdapter(val a: Activity, val articles: ArrayList<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding: ArticleListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val b = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(b)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        Log.d("trace","Link: ${articles[position].urlToImage}")
        holder.binding.articleText.text=articles[position].title
        Glide
            .with(holder.binding.articleImage.context)
            .load(articles[position].urlToImage)
            .error(R.drawable.broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.articleImage)

        val url =articles[position].url

        holder.binding.articleContainer.setOnClickListener {

            val i =Intent(Intent.ACTION_VIEW,url.toUri())
            a.startActivity(i)
        }

        holder.binding.shareFab.setOnClickListener {
            ShareCompat
                .IntentBuilder(a)
                .setType("text/plain")
                .setChooserTitle("Share article with: ")
                .setText(url)
                .startChooser()

        }
        var isFavourite = false
        holder.binding.favouritesFab.setOnClickListener {
            isFavourite = !isFavourite
            if (isFavourite) {
                holder.binding.favouritesFab.setImageResource(R.drawable.fav_heart)
                Toast.makeText(holder.itemView.context, "Added to favourites!", Toast.LENGTH_SHORT).show()
            }
            else {
                holder.binding.favouritesFab.setImageResource(R.drawable.un_fav_heart)
                Toast.makeText(holder.itemView.context, "Removed from favourites!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() = articles.size



}