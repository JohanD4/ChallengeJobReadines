package com.example.chanllegejobreadeniss.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import com.example.chanllegejobreadeniss.data.Favorites
import com.example.chanllegejobreadeniss.R
import com.example.chanllegejobreadeniss.data.CategoryResponse
import com.example.chanllegejobreadeniss.data.HighlightsResponse
import com.example.chanllegejobreadeniss.data.ListItemResponse
import com.example.chanllegejobreadeniss.databinding.ActivityMainBinding
import com.example.chanllegejobreadeniss.ui.recyclerView.ItemAdapter
import com.example.chanllegejobreadeniss.retroFit.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

  private lateinit var binding: ActivityMainBinding
  private lateinit var adapter: ItemAdapter
  private var itemsList = mutableListOf<ListItemResponse>()
  private var listFavorites: MutableSet<String>? = mutableSetOf()
  private var favorites = Favorites(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setUpRecyclerView()


    binding.SvCategories.setOnQueryTextListener(this)
  }

  private fun getCategoryId(query: String) {
    CoroutineScope(Dispatchers.IO).launch {
      val call =
        RetrofitServices.instance.create(ProductService::class.java).getCategories("MLA", 1, query)
      val categoryResponse: ArrayList<CategoryResponse>? = call.body()
      if (call.isSuccessful) {
        categoryResponse?.let {
          Log.i("RESPONSE CATEGORY OK", "$categoryResponse")
          if (it.size > 0) {
            getHighlights(it[0].category_id)
          } else {
            val snackbar = Snackbar.make(binding.root, getString(R.string.search_no_item), Snackbar.LENGTH_SHORT)
            snackbar.show()
          }
        }
      } else {
        Log.i("RESPONSE CATEGORY FAIL", "$categoryResponse")
        val snackbar = Snackbar.make(binding.root, getString(R.string.search_no_item), Snackbar.LENGTH_SHORT)
        snackbar.show()
      }

    }
  }


  private fun getHighlights(idCategory: String) {
    var ids = String()
    CoroutineScope(Dispatchers.IO).launch {
      val call =
        RetrofitServices.instance.create(ProductService::class.java).getHighlight("MLA", idCategory)
      val highlightResponse: HighlightsResponse? = call.body()
      if (call.isSuccessful) {
        highlightResponse?.let {
          Log.i("RESPONSE HIGHLIGHTS OK", "${highlightResponse.contents}")
          for (content in it.contents) {
            val idItem = content.id
            ids = "$ids,$idItem"
          }
          getListItemsDetail(ids)
        }
      } else {
        val snackbar = Snackbar.make(binding.root, getString(R.string.search_no_item), Snackbar.LENGTH_SHORT)
        snackbar.show()
        Log.i("RESPONSE HIGHLIGHTS FAIL", "$highlightResponse")
      }
    }
  }

  private fun getListItemsDetail(ids: String) {
    CoroutineScope(Dispatchers.IO).launch {
      val call = RetrofitServices.instance.create(ProductService::class.java).getProductList(ids)
      val itemsDetailResponse: List<ListItemResponse>? = call.body()
      runOnUiThread {
        if (call.isSuccessful) {
          itemsDetailResponse?.let {
            itemsList.clear()
            itemsList.addAll(it)
            adapter.notifyDataSetChanged()
          }
        } else {
          val snackbar = Snackbar.make(binding.root, getString(R.string.search_no_item), Snackbar.LENGTH_SHORT)
          snackbar.show()
        }
      }
    }
  }

  private fun setUpRecyclerView() {
    adapter = ItemAdapter(itemsList)
    binding.RvCategories.adapter = adapter
  }


  override fun onQueryTextSubmit(query: String?): Boolean {
    if (!query.isNullOrEmpty()) {
      itemsList.clear()
      adapter.notifyDataSetChanged()
      getCategoryId(query.lowercase())
      return true
    } else {
      return false
    }

  }


  override fun onQueryTextChange(newText: String?): Boolean {
    return true
  }


}