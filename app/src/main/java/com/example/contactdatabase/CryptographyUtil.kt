package com.example.contactdatabase

import android.text.TextUtils
import android.util.Base64
import java.security.MessageDigest

object CryptographyUtil {
    fun messageDigestSha256(toHash: String): String {
        if (TextUtils.isEmpty(toHash)) {
            return ""
        }
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = toHash.toByteArray(Charsets.UTF_8)
        digest.update(bytes, 0, bytes.size)
        return base64EncodeString(digest.digest())
    }

    fun base64EncodeString(toEncode: ByteArray): String {
        return String(Base64.encode(toEncode, Base64.DEFAULT))
    }
}