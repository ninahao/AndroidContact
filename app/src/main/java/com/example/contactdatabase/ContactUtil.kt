package com.example.contactdatabase

import android.content.Context
import android.provider.ContactsContract
import android.text.TextUtils

object ContactUtil {

    fun getContactTelephones(context: Context): List<String> {
        val phoneNUmberList = ArrayList<String>()
        val contentResolver = context.contentResolver
        val phoneNUmberCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER), null, null, null)
        if (phoneNUmberCursor != null) {
            while (phoneNUmberCursor.moveToNext()) {
                val telephone = phoneNUmberCursor.getString(phoneNUmberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val hashedPureNumber = CryptographyUtil.messageDigestSha256(PhoneNumberUtil.getPurePhoneNumber(context, telephone))
                if (!TextUtils.isEmpty(hashedPureNumber)) {
                    phoneNUmberList.add(hashedPureNumber)
                }
            }
        }
        phoneNUmberCursor?.close()
        return phoneNUmberList
    }

    fun getContacts(context: Context): List<Contact> {
        val contentResolver = context.contentResolver
        val contactList:MutableList<Contact> = mutableListOf()
        val contactProjection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
        )
        val contactCursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            contactProjection, null, null, null)
        if (contactCursor != null) {
            while (contactCursor.moveToNext()) {
                val contactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID))
                val contactName = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneListOfName = ArrayList<String>()
                val phoneProjection = arrayOf(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                )
                val phoneSelection = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?"
                val phoneCursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    phoneProjection, phoneSelection, arrayOf(contactId), null)
                if (phoneCursor != null) {
                    while (phoneCursor.moveToNext()) {
                        val phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        val hashedPureNumber = CryptographyUtil.messageDigestSha256(PhoneNumberUtil.getPurePhoneNumber(context, phone))
                        if (!TextUtils.isEmpty(hashedPureNumber)) {
                            phoneListOfName.add(hashedPureNumber)
                        }
                    }
                }
                phoneCursor?.close()
                contactList.add(Contact(contactName, phoneListOfName))
            }
        }
        contactCursor?.close()
        return contactList
    }

    fun getContactNameByPhone(contacts: List<Contact>, phoneList: List<String>): List<NamePhone> {
        val namePhoneList = mutableListOf<NamePhone>()
        phoneList.forEach { phone ->
            contacts.filter { contact ->
                contact.telephone.contains(phone)
            }.map {
                namePhoneList.add(NamePhone(it.name, phone))
            }
        }
        return namePhoneList
    }

}