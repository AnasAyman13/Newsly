package com.news.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.app.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var newsAdapter: RandomNewsAdapter
    private val newsList = ArrayList<Article>()


    private val categories = listOf(
        "Technology", "Science", "Sports", "Business", "Health", "Entertainment"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding = FragmentHomeBinding.inflate(inflater,container,false)
        newsAdapter = RandomNewsAdapter(newsList,requireActivity())
        binding.RandomNewsRec.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.RandomNewsRec.adapter = newsAdapter
loadRandomNews()
        binding.businessLL.setOnClickListener {
            val i = Intent(requireContext(), ArticleActivity::class.java)
            i.putExtra("category","business")
            startActivity(i)
        }
        binding.healthLL.setOnClickListener {
            val i = Intent(requireContext(), ArticleActivity::class.java)
            i.putExtra("category","health")
            startActivity(i)
        }
        binding.generalLL.setOnClickListener {
            val i = Intent(requireContext(), ArticleActivity::class.java)
            i.putExtra("category","general")
            startActivity(i)
        }
        binding.sportsLL.setOnClickListener {
            val i = Intent(requireContext(), ArticleActivity::class.java)
            i.putExtra("category","sports")
            startActivity(i)
        }
        binding.enterLL.setOnClickListener {
            val i = Intent(requireContext(), ArticleActivity::class.java)
            i.putExtra("category","entertainment")
            startActivity(i)
        }
        binding.techLL.setOnClickListener {
            val i = Intent(requireContext(), ArticleActivity::class.java)
            i.putExtra("category","technology")
            startActivity(i)
        }
        binding.scienceLL.setOnClickListener {
            val i = Intent(requireContext(), ArticleActivity::class.java)
            i.putExtra("category","science")
            startActivity(i)
        }

        return binding.root
    }
    fun loadRandomNews() {
        val retrofit2 = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val c = retrofit2.create(NewsCallable::class.java)
        c.getRandomNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                val articles = news?.articles ?: arrayListOf()
                newsList.clear()
                newsList.addAll(articles)
                newsAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("tag", "${t.message}")
            }
        })
    }
}
