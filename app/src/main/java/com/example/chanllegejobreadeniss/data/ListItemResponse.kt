package com.example.chanllegejobreadeniss.data

import com.google.gson.annotations.SerializedName

data class ListItemResponse(
  var code: Int,
  var body: Body
)

data class Body(
  var title: String,
  @SerializedName(value = "secure_thumbnail") var image: String,
  var price: String,
  var id: String,
  var pictures : List<Picture>

)

data class Picture(
  @SerializedName(value = "secure_url") var bigImage: String
)
