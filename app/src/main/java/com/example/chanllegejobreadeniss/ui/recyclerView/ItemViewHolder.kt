package com.example.chanllegejobreadeniss.ui.recyclerView

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.chanllegejobreadeniss.ui.DetailsActivity
import com.example.chanllegejobreadeniss.databinding.CategoryItemBinding
import com.example.chanllegejobreadeniss.data.ListItemResponse
import com.squareup.picasso.Picasso

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  private val binding = CategoryItemBinding.bind(view)

  fun bind(ListItemResponse: ListItemResponse) {
    // set all layout
    Picasso.get().load(ListItemResponse.body.image).into(binding.IvItem)
    binding.TvTitleItem.text = ListItemResponse.body.title
    binding.TvPrice.text = "$ ${ListItemResponse.body.price}"
    binding.container.setOnClickListener {
      val intent = Intent(it.context, DetailsActivity::class.java)
      intent.putExtra("itemImage", ListItemResponse.body.pictures[0].bigImage)
      intent.putExtra("itemId", ListItemResponse.body.id)
      intent.putExtra("itemTitle", ListItemResponse.body.title)
      intent.putExtra("itemPrice", ListItemResponse.body.price)
      it.context.startActivity(intent)
    }
  }
}