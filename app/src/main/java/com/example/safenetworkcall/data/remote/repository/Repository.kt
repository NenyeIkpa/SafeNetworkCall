package com.example.safenetworkcall.data.remote.repository

import com.example.safenetworkcall.data.SignUpUser
import com.example.safenetworkcall.data.remote.model.Company
import com.example.safenetworkcall.data.remote.model.SignUpResponse
import com.example.safenetworkcall.utils.Resource
import retrofit2.Response

interface Repository {

    suspend fun signUpUser(signUpUser: SignUpUser) : Resource<SignUpResponse>

    suspend fun getAllCompanies() : Resource<Company>
}