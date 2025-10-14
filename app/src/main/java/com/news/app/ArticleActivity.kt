package  com.news.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.news.app.databinding.ActivityArticleBinding

import com.news.app.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding
    private lateinit var category: String
    private lateinit var country: String
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra("category") ?: "general"

        val prefs = getSharedPreferences("countryPrefs",MODE_PRIVATE)
        country = prefs.getString("country","us") ?: "us"

        adapter = NewsAdapter(this, arrayListOf())
        binding.newsList.layoutManager = LinearLayoutManager(this)
        binding.newsList.adapter = adapter

        loadNews()

        binding.swipeRefresh.setOnRefreshListener { loadNews() }



    }
    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("countryPrefs", MODE_PRIVATE)
        val newCountry = prefs.getString("country", "us") ?: "us"
        if (newCountry != country) {
            country = newCountry
            loadNews()
        }
    }

    private fun loadNews() {
        binding.progress.isVisible = true
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val c = retrofit.create(NewsCallable::class.java)
        c.getNews(
            country = country ,
            category = category
        ).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val articles = response.body()?.articles!!


                showNews(articles)
                binding.progress.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Toast.makeText(this@ArticleActivity, "Failed to load news!", Toast.LENGTH_SHORT).show()
                binding.progress.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }
        })
    }

    private fun showNews(articles: ArrayList<Article>) {
        val adapter = NewsAdapter(this, articles)
        binding.newsList.adapter = adapter
    }

}
