package com.android.galleryapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GalleryRoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var image: ByteArray? = null
) {
    constructor(imageUrl: String?, image: ByteArray?) : this(0, imageUrl, image)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GalleryRoomEntity

        if (id != other.id) return false
        if (imageUrl != other.imageUrl) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}
