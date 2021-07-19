package com.android.galleryapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.android.galleryapp.R
import com.android.galleryapp.data.DatabaseBuilder
import com.android.galleryapp.data.DatabaseHelperImpl
import com.android.galleryapp.data.GalleryRoomEntity
import com.android.galleryapp.model.GalleryUIModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_gallery.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class GalleryAdapter(
    private val mContext: Context
) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    val imageDataList = mutableListOf<GalleryUIModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_gallery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = imageDataList.get(position)
        if (country.imageByteArray == null) {
            Picasso.get()
                .load(country.url)
                .into(object : com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        holder.ivCountryFlag.setImageBitmap(bitmap)
                        GlobalScope.launch {
                            val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(mContext))
                            val stream = ByteArrayOutputStream()
                            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                            val bitmapdata = stream.toByteArray()
                            dbHelper.insertOnly(
                                GalleryRoomEntity(
                                    country.url,
                                    bitmapdata
                                )
                            )
                        }
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }
                })
        } else {
            val bitmap = BitmapFactory.decodeByteArray(
                country.imageByteArray, 0,
                country.imageByteArray.size
            )
            holder.ivCountryFlag.setImageBitmap(bitmap)
        }
    }

    override fun getItemCount(): Int {
        return imageDataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCountryFlag: AppCompatImageView = itemView.ivCountryFlag
        val tvCountryName: AppCompatTextView = itemView.tvCountryName
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setData(dataList: List<GalleryUIModel>) {
        imageDataList.addAll(dataList)
    }
}
