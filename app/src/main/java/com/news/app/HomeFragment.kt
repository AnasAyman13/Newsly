package com.news.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private val categories = listOf(
        "Technology", "Science", "Sports", "Business", "Health", "Entertainment"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // Random news placeholder (محجوز)
        val randomRecycler = root.findViewById<RecyclerView>(R.id.RandomNewsRacycler)
        randomRecycler.visibility = View.GONE

        // Categories
        val categoriesRecycler = root.findViewById<RecyclerView>(R.id.categoriesRacycler)
        categoriesRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        categoriesRecycler.adapter = CategoriesAdapter(categories) { category ->
            // عند الضغط على الكاتيجوري — ممكن تعمل Toast أو navigate
            // Toast.makeText(requireContext(), "Selected $category", Toast.LENGTH_SHORT).show()
        }

        return root
    }
}
