package com.android.galleryapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.galleryapp.R
import com.android.galleryapp.adapter.GalleryAdapter
import com.android.galleryapp.data.DatabaseBuilder
import com.android.galleryapp.data.DatabaseHelperImpl
import com.android.galleryapp.ui.viewmodel.GalleryViewModel
import com.android.galleryapp.utils.AppConstants
import com.android.galleryapp.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class GalleryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dbHelper: DatabaseHelperImpl
    private lateinit var mAdapter: GalleryAdapter
    private val postViewModel: GalleryViewModel by viewModels()
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(this))
        setUI()
        setListeners()
        setObserver()
    }

    private fun setListeners() {
        ivProfile.setOnClickListener(this)
    }

    private fun setObserver() {
        postViewModel.getPostLiveData().observe(this) {
            if (mAdapter.itemCount == 0) {
                hideProgressBar(progressBarCenter)
            }
            hideProgressBar(progressBarBottom)
            mAdapter.setData(it)
            mAdapter.notifyDataSetChanged()
        }
        if (mAdapter.itemCount == 0) {
            progressBarCenter.visibility = View.VISIBLE
        }
        fetchData()
    }

    private fun hideProgressBar(progressBar: ProgressBar) {
        if (progressBar.visibility == View.VISIBLE) {
            progressBar.visibility = View.GONE
        }
    }

    private fun setUI() {
        ivBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.gallery)
        GridLayoutManager(
            this,
            2,
            RecyclerView.VERTICAL,
            false
        ).apply {
            rvGallery.layoutManager = this
        }

        mAdapter = GalleryAdapter(this)
        rvGallery.adapter = mAdapter

        rvGallery.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val layoutManager = rvGallery.layoutManager as GridLayoutManager
                    val visibleItemCount = layoutManager.findLastCompletelyVisibleItemPosition() + 1
                    if (visibleItemCount == layoutManager.itemCount) {
                        progressBarBottom.visibility = View.VISIBLE
                        currentPage++
                        fetchData()
                    }
                }
            }
        })
    }

    private fun fetchData() {
        if (AppUtils.isNetworkAvailable(this)) {
            postViewModel.getPost(currentPage, AppConstants.PER_PAGE_ITEM_LIMIT)
        } else {
            postViewModel.getPostFromRoomDB(currentPage, AppConstants.PER_PAGE_ITEM_LIMIT, dbHelper)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivProfile -> startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}