package com.android.galleryapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GalleryRoomEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun galleryDao(): GalleryDao

}