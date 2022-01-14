package com.example.chanllegejobreadeniss.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chanllegejobreadeniss.data.Favorites
import com.example.chanllegejobreadeniss.R
import com.example.chanllegejobreadeniss.databinding.ActivityDetailsBinding
import com.example.chanllegejobreadeniss.data.ItemDetailResponse
import com.example.chanllegejobreadeniss.retroFit.ProductService
import com.example.chanllegejobreadeniss.retroFit.RetrofitServices
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {

  private lateinit var biding: ActivityDetailsBinding
  private var favorites = Favorites(this)
  private var itemImage: String? = null
  private var itemId: String? = null
  private var itemTitle: String? = null
  private var itemPrice: String? = null
  private var listFavorites: MutableSet<String>? = mutableSetOf()
  private var status: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    biding = ActivityDetailsBinding.inflate(layoutInflater)
    setContentView(biding.root)

    itemImage = intent.getStringExtra("itemImage")
    itemId = intent.getStringExtra("itemId")
    itemTitle = intent.getStringExtra("itemTitle")
    itemPrice = intent.getStringExtra("itemPrice")

    validateFavorite()

    Picasso.get().load(itemImage).into(biding.IvItemDetail)
    biding.TvTitleSmall.text = itemTitle
    biding.TvTitle.text = itemTitle
    biding.TvPriceDetail.text = "$ $itemPrice"


    biding.TvAddFavorite.setOnClickListener {
      if (status) { //heart status full
        deleteFavorite()
        biding.IvHeart.setImageResource(R.drawable.ic_empty_heart)
        biding.TvAddFavorite.text = getString(R.string.add_favorites)
      } else { // heart status empty
        itemId?.let { itemId -> addFavorite(itemId) }
        biding.IvHeart.setImageResource(R.drawable.ic_full_heart)
        biding.TvAddFavorite.text = getString(R.string.delete_favorites)
      }
    }

    biding.IvHeart.setOnClickListener {
      if (status) { //heart status full
        deleteFavorite()
        biding.IvHeart.setImageResource(R.drawable.ic_empty_heart)
        biding.TvAddFavorite.text = getString(R.string.add_favorites)
      } else { // heart status empty
        itemId?.let { itemId -> addFavorite(itemId) }
        biding.IvHeart.setImageResource(R.drawable.ic_full_heart)
        biding.TvAddFavorite.text = getString(R.string.delete_favorites)
      }
    }

    itemId?.let {
      getDetailItem(it)
    }
  }


  private fun getDetailItem(id: String) {
    CoroutineScope(Dispatchers.IO).launch {
      val call = RetrofitServices.instance.create(ProductService::class.java).getItemDetail(id)
      val itemDetail: ItemDetailResponse? = call.body()
      runOnUiThread {
        if (call.isSuccessful) {
          itemDetail?.let {
            biding.TvDescription.text = it.plain_text
          }
        }
      }
    }
  }


  private fun validateFavorite() {
    listFavorites = favorites.getFavorites()
    listFavorites?.let {
      if (it.contains(itemId)) {
        status = true
        biding.IvHeart.setImageResource(R.drawable.ic_full_heart)
        biding.TvAddFavorite.text = getString(R.string.delete_favorites)
      }
    }
  }

  private fun addFavorite(id: String) {
    listFavorites = favorites.getFavorites()
    listFavorites?.let {
      status = true
      it.add(id)
      favorites.updateFavorites(it)
      listFavorites = favorites.getFavorites()
    }
    //Check button favorites
  }

  private fun deleteFavorite() {
    listFavorites = favorites.getFavorites()
    listFavorites?.let {
      status = false
      it.remove(itemId)
      favorites.updateFavorites(it)
    }
  }


}