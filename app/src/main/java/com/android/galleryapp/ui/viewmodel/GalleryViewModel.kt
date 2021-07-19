package com.android.galleryapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.galleryapp.data.DatabaseHelperImpl
import com.android.galleryapp.model.GalleryUIModel
import com.android.galleryapp.ui.repo.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GalleryViewModel @Inject constructor(private val galleryRepository: GalleryRepository) :
    ViewModel() {

    private val _post: MutableLiveData<List<GalleryUIModel>> = MutableLiveData()
    private val post: LiveData<List<GalleryUIModel>> = _post

    fun getPostLiveData(): LiveData<List<GalleryUIModel>> = post

    fun getImagesList(currentPage: Int, perPageLimit: Int) = viewModelScope.launch {
        val posts = galleryRepository.getGalleryList(currentPage, perPageLimit)
        val uiDataList = mutableListOf<GalleryUIModel>()
        posts.forEach {
            val model = GalleryUIModel(it.urls.thumb, null)
            uiDataList.add(model)
        }
        _post.value = uiDataList
    }

    fun getImagesFromRoomDB(currentPage: Int, perPageLimit: Int, dbHelper: DatabaseHelperImpl) =
        viewModelScope.launch {
            val offSet = currentPage * perPageLimit
            val offlineRecordsList = dbHelper.getOfflineRecords(perPageLimit, offSet)
            val uiDataList = mutableListOf<GalleryUIModel>()

            offlineRecordsList.forEach {
                val model = GalleryUIModel(it.imageUrl.toString(), it.image)
                uiDataList.add(model)
            }
            _post.value = uiDataList
        }
}