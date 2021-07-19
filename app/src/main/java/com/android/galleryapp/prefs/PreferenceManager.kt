package com.eid.h2hospital.ui.data

import android.content.Context
import android.content.SharedPreferences

interface PreferenceManager {

    fun getPreferences(context: Context?): SharedPreferences?

    fun editPreferences(context: Context?): SharedPreferences.Editor?

    fun setString(
        context: Context?,
        key: String?,
        value: String?
    )

    fun getString(
        context: Context?,
        key: String?
    ): String?

    fun setBoolean(
        context: Context?,
        key: String?,
        value: Boolean
    )

    fun getBoolean(
        context: Context?,
        key: String?
    ): Boolean?

    fun setInteger(
        context: Context?,
        key: String?,
        value: Int
    )

    fun getInteger(context: Context?, key: String?): Int?

    fun setFloat(
        context: Context?,
        key: String?,
        value: Float
    )

    fun getFloat(
        context: Context?,
        key: String?
    ): Float?
}