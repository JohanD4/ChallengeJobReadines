package com.example.chanllegejobreadeniss.retroFit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServices {
  val instance: Retrofit
    get() {
      return Retrofit.
      Builder().
      baseUrl("https://api.mercadolibre.com/").
      addConverterFactory(GsonConverterFactory.create()).
      build()

    }
}