package  com.news.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible

import com.news.app.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleActivity : AppCompatActivity() {

/*    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadNews()

      //  binding.swipeRefresh.setOnRefreshListener { loadNews() }

    }

    private fun loadNews() {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val c = retrofit.create(NewsCallable::class.java)
        c.getNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val articles = response.body()?.articles!!

                articles.removeAll{
                    it.title == "[Removed]"
                    it.title=="[Stocks Rise as Government Shutdown Looms]"
                }

                //Log.d("trace", "Data: $articles")
                showNews(articles)
                binding.progress.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
               // Log.d("trace", "Error: ${t.message}")
                binding.progress.isVisible = false
                binding.swipeRefresh.isRefreshing = false
            }
        })
    }

    private fun showNews(articles: ArrayList<Article>) {
        val adapter = NewsAdapter(this, articles)
        binding.newsList.adapter = adapter
    }*/

}
