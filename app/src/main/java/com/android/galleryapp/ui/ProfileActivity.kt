package com.android.galleryapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.galleryapp.R
import com.android.galleryapp.listeners.OnDialogGenericListener
import com.android.galleryapp.utils.AppUtils
import com.android.galleryapp.utils.DialogUtils
import com.eid.h2hospital.ui.data.PreferenceHelper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.toolbar.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setUI()
        setListeners()
    }

    private fun setUI() {
        ivProfile.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.profile)

        val displayName =
            PreferenceHelper.getInstance()?.getString(this, PreferenceHelper.DISPLAY_NAME)
        val email =
            PreferenceHelper.getInstance()?.getString(this, PreferenceHelper.EMAIL)
        val photoURL =
            PreferenceHelper.getInstance()?.getString(this, PreferenceHelper.PROFILE_URL)

        if (!TextUtils.isEmpty(displayName)) {
            tvDisplayName.text = displayName
        }

        if (!TextUtils.isEmpty(email)) {
            tvEmail.text = email
        }

        if (!TextUtils.isEmpty(photoURL)) {
            Picasso.get()
                .load(photoURL)
                .placeholder(R.drawable.ic_profile_placeholder)
                .into(ivProfileImg)
        }
    }

    private fun setListeners() {
        ivBack.setOnClickListener(this)
        btnLogout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBack -> finish()
            R.id.btnLogout -> signOut()
        }
    }

    private fun signOut() {
        if (AppUtils.isNetworkAvailable(this)) {
            Firebase.auth.signOut()
            PreferenceHelper.getInstance()?.clearAppDataPreference(this)
            startActivity(Intent(this, SignInActivity::class.java))
            finishAffinity()
        } else {
            DialogUtils.showConfirmationDialog(
                this,
                getString(R.string.internet_not_available),
                getString(R.string.okay),
                "",
                false,
                object : OnDialogGenericListener {
                    override fun onPositiveClick() {

                    }

                    override fun onNegativeClick() {

                    }
                })
        }
    }
}