package com.example.contactdatabase

data class Contact(
    val name: String,
    val telephone: List<String>
)

data class NamePhone(
    val name: String,
    val phone: String
)
