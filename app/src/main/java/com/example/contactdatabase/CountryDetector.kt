package com.example.contactdatabase

import android.content.Context
import android.telephony.TelephonyManager

import android.text.TextUtils
import java.util.*


class CountryDetector private constructor(context: Context) {
    private val mTelephonyManager: TelephonyManager
    val currentCountryIso: String
        get() {
            var result: String? = null
            val networkCountryCodeAvailable = isNetworkCountryCodeAvailable
            if (networkCountryCodeAvailable) {
                result = networkBasedCountryIso
            }
            if (TextUtils.isEmpty(result)) {
                result = simBasedCountryIso
            }
            if (TextUtils.isEmpty(result)) {
                result = localeBasedCountryIso
            }
            if (TextUtils.isEmpty(result)) {
                result = DEFAULT_COUNTRY_ISO
            }
            return result!!.toUpperCase(Locale.getDefault())
        }

    /**
     * @return the country code of the current telephony network the user is connected to.
     */
    private val networkBasedCountryIso: String
        private get() = mTelephonyManager.networkCountryIso

    /**
     * @return the country code of the SIM card currently inserted in the device.
     */
    private val simBasedCountryIso: String
        private get() = mTelephonyManager.simCountryIso

    /**
     * @return the country code of the user's currently selected locale.
     */
    private val localeBasedCountryIso: String?
        private get() {
            val defaultLocale: Locale = Locale.getDefault()
            return if (defaultLocale != null) {
                defaultLocale.getCountry()
            } else null
        }

    // On CDMA TelephonyManager.getNetworkCountryIso() just returns the SIM's country code.
    // In this case, we want to ignore the value returned and fallback to location instead.
    private val isNetworkCountryCodeAvailable: Boolean
        private get() = mTelephonyManager.phoneType == TelephonyManager.PHONE_TYPE_GSM

    companion object {
        private const val DEFAULT_COUNTRY_ISO = "AE"
        private var sInstance: CountryDetector? = null
        fun getInstance(context: Context): CountryDetector? {
            if (sInstance == null) {
                synchronized(CountryDetector::class.java) {
                    if (sInstance == null) {
                        sInstance = CountryDetector(context)
                    }
                }
            }
            return sInstance
        }
    }

    init {
        mTelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }
}