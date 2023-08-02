package com.ztb.pinkoo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtil {

    private var editor: SharedPreferences.Editor? = null
    private var mcontext: Context? = null
    private var preferences: SharedPreferences? = null

    @SuppressLint("CommitPrefEdits")
    constructor(context: Context?) {
        if (context != null) {
            mcontext = context
            preferences = context.getSharedPreferences(
                "MyAppSettings", Context.MODE_PRIVATE)
            editor = preferences!!.edit()
        }
    }
    fun getPrefs(context: Context): SharedPreferences? {
        return context.getSharedPreferences("MyAppSettings", Context.MODE_PRIVATE)
    }

    //set String Preference
    fun setStringPreferences(key: String, value: String) {
        editor!!.putString(key, value)
        editor!!.commit()
    }
    //get String Preference
    fun getStringPreferences(key: String): String? {
        return preferences!!.getString(key, null)
    }


    //set Boolean Preference
    fun setBooleanPreferences(key: String, value: Boolean) {
        editor!!.putBoolean(key, value)
        editor!!.commit()
    }
    //get Boolean Preference
    fun getBooleanPreferences(key: String): Boolean {
        return preferences!!.getBoolean(key, false)
    }



    //set int prfrence


    fun setIntPreferences(key: String, value: Int) {
        editor!!.putInt(key, value)
        editor!!.commit()
    }
    //get String Preference
    fun getIntPreferences(key: String): Int {
        return preferences!!.getInt(key, 0)
    }



    //remove all preference
    fun removeAllPreferences() {
        editor!!.clear().commit()
    }

}