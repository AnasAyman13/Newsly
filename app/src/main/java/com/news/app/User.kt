import com.news.app.Article

data class User(
    val ID: String = "",
    val Email: String = "",
    val Password: String = "",
    val favArticles: ArrayList<Article> = arrayListOf()
)