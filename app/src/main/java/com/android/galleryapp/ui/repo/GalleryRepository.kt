package com.android.galleryapp.ui.repo

import com.android.galleryapp.model.GalleryResponseModel
import com.android.galleryapp.network.ApiRequest
import com.android.galleryapp.network.NetworkConstants
import javax.inject.Inject

class GalleryRepository @Inject constructor(private val apiRequest: ApiRequest) {
    suspend fun getGalleryList(
        currentPage: Int,
        perPageLimit: Int,
    ): List<GalleryResponseModel> {
        return apiRequest.getGalleryList(currentPage, perPageLimit, NetworkConstants.CLIENT_ID)
    }
}