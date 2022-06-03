package com.example.safenetworkcall.data.remote.network

import com.example.safenetworkcall.data.SignUpUser
import com.example.safenetworkcall.data.remote.model.Company
import com.example.safenetworkcall.data.remote.model.SignUpResponse
import com.example.safenetworkcall.utils.Resource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface CallApi {

    @POST("/CompanyManager")
    suspend fun signUpUser(@Body supplier: SignUpUser): Response<SignUpResponse>

    @GET("/api/Company/GetAllCompanies")
    suspend fun getCompanies(): Response<Company>

}