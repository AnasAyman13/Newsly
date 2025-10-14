package com.news.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val categories = listOf(
        "Technology", "Science", "Sports", "Business", "Health", "Entertainment"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding = FragmentHomeBinding.inflate(inflater,container,false)

        // Random news placeholder (محجوز)
       /* val randomRecycler = root.findViewById<RecyclerView>(R.id.RandomNewsRacycler)
        randomRecycler.visibility = View.GONE
        */
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
}
