package com.example.safenetworkcall.data.remote.model

data class User(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val gender: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val phoneNumber: String,
    val location: Location,
    val profilePictureUrl: String? = null
)

