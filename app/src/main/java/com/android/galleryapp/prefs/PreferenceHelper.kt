package com.eid.h2hospital.ui.data

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper : PreferenceManager {

    private var prefManager: PreferenceHelper? = null

    private val PREFERENCE_NAME = "GapperyApp"

    /* preference mode - private , only be accessed by this application*/
    private val PRIVATE_MODE = 0


    /**
     * Singleton class which will be accessed through this instance
     *
     * @return Singleton class instance
     */
    fun getInstance(): PreferenceHelper? {
        if (prefManager == null) {
            prefManager = PreferenceHelper
        }
        return prefManager
    }

    /**
     * To Clear all user related data.
     */
    fun clearAppDataPreference(context: Context) {
        editPreferences(context)?.clear()?.apply()
    }

    override fun getPreferences(context: Context?): SharedPreferences? {
        return context?.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)
    }

    override fun editPreferences(context: Context?): SharedPreferences.Editor? {
        return getPreferences(context)?.edit()
    }

    override fun setString(context: Context?, key: String?, value: String?) {
        editPreferences(context)?.putString(key, value)?.apply()
    }

    override fun getString(context: Context?, key: String?): String? {
        return getPreferences(context)?.getString(key, "")
    }

    override fun setBoolean(context: Context?, key: String?, value: Boolean) {
        editPreferences(context)?.putBoolean(key, value)?.apply()
    }

    override fun getBoolean(context: Context?, key: String?): Boolean? {
        return getPreferences(context)?.getBoolean(key, false)
    }

    override fun setInteger(context: Context?, key: String?, value: Int) {
        editPreferences(context)?.putInt(key, value)?.apply()
    }

    override fun getInteger(context: Context?, key: String?): Int? {
        return getPreferences(context)?.getInt(key, 0)
    }

    override fun setFloat(context: Context?, key: String?, value: Float) {
        editPreferences(context)?.putFloat(key, value)?.apply()
    }

    override fun getFloat(context: Context?, key: String?): Float? {
        return getPreferences(context)?.getFloat(key, -1f)
    }

    const val PROFILE_URL = "PROFILE_URL"
    const val DISPLAY_NAME = "DISPLAY_NAME"
    const val EMAIL = "EMAIL"
}