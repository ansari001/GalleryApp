package com.android.galleryapp.data

interface DatabaseHelper {

    /*suspend fun getUsers(): List<User>*/

    suspend fun insertAll(users: List<GalleryRoomEntity>)
    suspend fun insertOnly(users: GalleryRoomEntity)
    suspend fun getOfflineRecords(perPageLimit: Int, recordToFetch: Int): List<GalleryRoomEntity>

}