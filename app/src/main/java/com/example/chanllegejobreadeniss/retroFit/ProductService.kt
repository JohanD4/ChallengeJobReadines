package com.example.chanllegejobreadeniss.retroFit

import com.example.chanllegejobreadeniss.data.CategoryResponse
import com.example.chanllegejobreadeniss.data.HighlightsResponse
import com.example.chanllegejobreadeniss.data.ItemDetailResponse
import com.example.chanllegejobreadeniss.data.ListItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
  //Function that accepts a COUNTRY_ID(MLA, MLC, MLO..) and a query to predict a possible
  //category as a response, by default the response its an array but we are only using the
  // first value. Limit only defines how many results you ask for.

  //@Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
  @Headers("Authorization: Bearer APP_USR-1031238506811576-011416-8b2f941c35c410f9a933982f9065c689-280865594")
  @GET("sites/{COUNTRY_ID}/domain_discovery/search")
  suspend fun getCategories(
    @Path("COUNTRY_ID") siteId: String,
    @Query("limit") limit: Int = 1,
    @Query("q") searchTerm: String
  ): Response<ArrayList<CategoryResponse>>


  //Function that takes a COUNTRY_ID(MLA, MLC, MLO..) and a CATEGORY_ID and returns a list of
  //the top 20 (Highlight) products of that category
  @Headers("Authorization: Bearer APP_USR-1031238506811576-011416-8b2f941c35c410f9a933982f9065c689-280865594")
  @GET("highlights/{COUNTRY_ID}/category/{CATEGORY_ID}")
  suspend fun getHighlight(
    @Path("COUNTRY_ID") siteId: String,
    @Path("CATEGORY_ID") categoryId: String
  ): Response<HighlightsResponse>

  //Function that accepts a list of ids separated by comma and returns a list of products
  @Headers("Authorization: Bearer APP_USR-1031238506811576-011416-8b2f941c35c410f9a933982f9065c689-280865594")
  @GET("/items")
  suspend fun getProductList(@Query("ids") productIdList: String): Response<List<ListItemResponse>>

  //(Optional!)Function that by getting a product id returns the details of that particular product
  /*@Headers("Authorization: Bearer APP_USR-1031238506811576-011416-8b2f941c35c410f9a933982f9065c689-280865594")
  @GET("/products/{PRODUCT_ID}")
  suspend fun getProductById(@Path("PRODUCT_ID")productId:String): Response<Details>*/

  @Headers("Authorization: Bearer APP_USR-1031238506811576-011416-8b2f941c35c410f9a933982f9065c689-280865594")
  @GET("items/{ITEM_ID}/description")
  suspend fun getItemDetail(
    @Path("ITEM_ID") siteId: String,
  ): Response<ItemDetailResponse>

}