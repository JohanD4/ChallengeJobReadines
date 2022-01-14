package com.example.chanllegejobreadeniss.data

import com.google.gson.annotations.SerializedName

data class HighlightsResponse(@SerializedName(value = "content") var contents: List<Content>)

data class Content(
  var id: String,
  var position: Int,
  var type: String
)