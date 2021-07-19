package com.android.galleryapp.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.android.galleryapp.R
import com.android.galleryapp.listeners.OnDialogGenericListener

class DialogUtils {
    companion object {
        @JvmStatic
        fun showConfirmationDialog(
            mActivity: Activity,
            title: String,
            positiveBtnText: String,
            negativeBtnText: String,
            showNegativeButton: Boolean,
            mListener: OnDialogGenericListener
        ) {
            val dialog = Dialog(mActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_confirmation)
            dialog.window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val tvTitle: AppCompatTextView = dialog.findViewById(R.id.tvTitle)
            val btnOk: AppCompatButton = dialog.findViewById(R.id.btnOk)
            val btnCancel: AppCompatButton = dialog.findViewById(R.id.btnCancel)

            tvTitle.text = title
            btnOk.text = positiveBtnText
            btnCancel.text = negativeBtnText

            if (showNegativeButton) {
                btnCancel.visibility = View.VISIBLE
            } else {
                btnCancel.visibility = View.GONE
            }

            btnOk.setOnClickListener {
                dialog.dismiss()
                mListener.onPositiveClick()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
                mListener.onNegativeClick()
            }
            if (!mActivity.isFinishing) {
                dialog.show()
            }
        }
    }
}