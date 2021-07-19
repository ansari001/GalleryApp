package com.android.galleryapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GalleryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<GalleryRoomEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnly(users: GalleryRoomEntity)

    @Query("SELECT * FROM GalleryRoomEntity LIMIT :limit OFFSET :offset")
    suspend fun getOfflineRecords(limit: Int, offset: Int): List<GalleryRoomEntity>

    /*@Delete
    suspend fun delete(user: User)*/

}