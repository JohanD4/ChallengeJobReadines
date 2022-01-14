package com.example.chanllegejobreadeniss.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chanllegejobreadeniss.R
import com.example.chanllegejobreadeniss.data.ListItemResponse

class ItemAdapter(private var itemList: MutableList<ListItemResponse>) :
  RecyclerView.Adapter<ItemViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    return ItemViewHolder(layoutInflater.inflate(R.layout.category_item, parent, false))
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

    val itemPosition = itemList[position]
    holder.bind(itemPosition)
  }

  override fun getItemCount() = itemList.size


}