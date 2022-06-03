package com.example.safenetworkcall.data.remote.model

data class Location(
    val city: String,
    val country: String? = null,
    val houseNumber: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val otherDetails: String? = null,
    val state: String? = null,
    val street: String? = null
)
