package com.example.contactdatabase

import android.content.Context
import com.google.i18n.phonenumbers.PhoneNumberUtil


object PhoneNumberUtil {

    fun getPurePhoneNumber(context: Context, phoneNumber: String): String {
        var pureNumber = ""
        val phoneUtil = PhoneNumberUtil.getInstance()
        pureNumber = try {
            phoneUtil.parse(phoneNumber, "ZZ").nationalNumber.toString()
        } catch (e: Exception) {
            phoneUtil.parse(phoneNumber, CountryDetector.getInstance(context)?.currentCountryIso).nationalNumber.toString()
        }
        return pureNumber
    }
}