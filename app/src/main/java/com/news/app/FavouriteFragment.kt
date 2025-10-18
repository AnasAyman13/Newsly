package com.news.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.news.app.databinding.FragmentFavouriteBinding
class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var adapter: NewsAdapter
    private val articles = ArrayList<Article>()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentFavouriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NewsAdapter(requireActivity(),articles,isFavoritesPage = true)
        binding.favRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favRecyclerView.adapter = adapter

        loadFavorites()
    }
    private fun loadFavorites() {
        val user = auth.currentUser ?: return
        db.collection("Users").document(user.uid)
            .get()
            .addOnSuccessListener { doc ->
                val favList = doc.get("favArticles") as? List<Map<String, String>>
                articles.clear()
                favList?.forEach { map ->
                    val article = Article(
                        title = map["title"] ?: "",
                        url = map["url"] ?: "",
                        urlToImage = map["urlToImage"] ?: ""
                    )
                    articles.add(article)
                }
                adapter.notifyDataSetChanged()
            }
    }


}