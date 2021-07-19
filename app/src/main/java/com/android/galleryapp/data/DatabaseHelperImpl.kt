package com.android.galleryapp.data

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    /*override suspend fun getUsers(): List<GalleryRoomEntity> = appDatabase.galleryDao().getAll()*/
    override suspend fun insertAll(users: List<GalleryRoomEntity>) =
        appDatabase.galleryDao().insertAll(users)

    override suspend fun insertOnly(users: GalleryRoomEntity) =
        appDatabase.galleryDao().insertOnly(users)

    override suspend fun getOfflineRecords(
        perPageLimit: Int,
        recordToFetch: Int
    ): List<GalleryRoomEntity> =
        appDatabase.galleryDao().getOfflineRecords(perPageLimit, recordToFetch)
}