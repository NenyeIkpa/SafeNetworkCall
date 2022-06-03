package com.example.safenetworkcall.data.remote.model

data class Company(
    val `data`: List<Data>,
    val errors: Any,
    val message: String,
    val success: Boolean
)
data class Data(
    val companyId: String,
    val companyName: String
)
