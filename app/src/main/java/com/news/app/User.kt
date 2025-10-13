data class User(
    val ID: String = "",
    val Email: String = "",
    val Password: String = "",
    val favArticleIds: ArrayList<String> = arrayListOf()
)