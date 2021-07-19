package com.android.galleryapp.model

data class GalleryResponseModel(
    val alt_description: String,
    val urls: Urls
)

data class Urls(
    val regular: String,
    val small: String,
    val thumb: String,
)