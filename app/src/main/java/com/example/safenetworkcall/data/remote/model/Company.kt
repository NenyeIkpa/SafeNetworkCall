package com.example.safenetworkcall.data.remote.model

import com.google.gson.annotations.SerializedName

data class Company(
    val `data`: List<Data>,
    val errors: Any,
    val message: String,
    val success: Boolean
)
data class Data(
    @SerializedName("id")
    val companyId: String,
    val companyName: String
)
