package com.example.contactdatabase

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtil {
    private fun getSharedPreferences(context: Context, fileName: String = "global_money_information"): SharedPreferences {
        return context.getSharedPreferences(fileName, Activity.MODE_PRIVATE)
    }

    fun getIntValue(context: Context, fileName: String = "global_money_information", key: String = "upload_day"): Int {
        return getSharedPreferences(context, fileName).getInt(key, 0)
    }

    fun putIntValue(context: Context, fileName: String = "global_money_information", key: String = "upload_day", value: Int) {
        val edit = getSharedPreferences(context, fileName).edit()
        edit.putInt(key, value).apply()
    }
}