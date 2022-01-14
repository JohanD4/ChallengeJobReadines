package com.example.chanllegejobreadeniss.data

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.chanllegejobreadeniss.R

open class Favorites(private var context: Context) {

  fun getFavorites(): MutableSet<String> {
    val sharedPref = context.getSharedPreferences(
      context.getString(R.string.preferences),
      AppCompatActivity.MODE_PRIVATE
    )

    return sharedPref.getStringSet(context.getString(R.string.favorites), mutableSetOf()) as MutableSet<String>

  }


  fun updateFavorites(listFavorites: MutableSet<String>) {
    val sharedPref = context.getSharedPreferences(
      context.getString(R.string.preferences),
      AppCompatActivity.MODE_PRIVATE
    )
    val editor = sharedPref.edit()
    editor.putStringSet(context.getString(R.string.favorites), listFavorites)
    editor.apply()
  }


}