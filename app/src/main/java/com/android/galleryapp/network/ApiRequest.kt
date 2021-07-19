package com.android.galleryapp.network

import com.android.galleryapp.model.GalleryResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    /*@GET("photos?client_id=97uEQ6poJCXdgiHG492nvJ_GW7bpdP_MIreUsAAqYvc")
    suspend fun getCountryList(): List<CountryModel>*/

    @GET("photos")
    suspend fun getGalleryList(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("client_id") client_id: String,
    ): List<GalleryResponseModel>
}