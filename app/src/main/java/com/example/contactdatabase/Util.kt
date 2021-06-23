package com.example.contactdatabase

import android.content.Context
import java.util.*

object Util {
    fun isNeedUpload(context: Context): Boolean {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val lastUploadDay = SharedPreferencesUtil.getIntValue(context)
        if (lastUploadDay == 0 || today != lastUploadDay) {
            return true
        }
        return false
    }
}