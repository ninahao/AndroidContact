package com.example.contactdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getContact(view: View) {
        Thread(Runnable {
            val phoneList = ContactUtil.getContactTelephones(this@MainActivity)
            Log.i("TAG", "phone list size: ${phoneList.size}")
            phoneList.forEach {
                Log.i("TAG", "phone: $it")
            }
            Log.i("TAG", "finish")
        }).start()
    }

    fun getContactName(view: View) {
        if (Util.isNeedUpload(this)) {
            Thread(Runnable {
                Log.i("TAG", "start")
                val phoneList = ContactUtil.getContactTelephones(this@MainActivity)
                Log.i("TAG", "phone list size: ${phoneList.size}")
                val contacts = ContactUtil.getContacts(this@MainActivity)
                Log.i("TAG", "contacts list size: ${contacts.size}")
                val namePhone = ContactUtil.getContactNameByPhone(contacts, phoneList)
                Log.i("TAG", "namePhone list size: ${namePhone.size}")
                Log.i("TAG", "finish")
                namePhone.forEach {
                    Log.i("TAG", "name: ${it.name}, phone: ${it.phone}")
                }
            }).start()
        } else {
            Toast.makeText(this, "have uploaded today", Toast.LENGTH_LONG).show()
        }
    }

    fun parsePhoneNumber(view: View) {
        val phoneNumber = input.text.toString()
        val pureNumber = PhoneNumberUtil.getPurePhoneNumber(this, phoneNumber)
        Log.i("TAG", "pureNumber: $pureNumber")
        tv.text = pureNumber
    }
}