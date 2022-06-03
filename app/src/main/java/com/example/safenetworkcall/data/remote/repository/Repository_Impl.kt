package com.example.safenetworkcall.data.remote.repository

import com.example.safenetworkcall.data.SignUpUser
import com.example.safenetworkcall.data.remote.model.Company
import com.example.safenetworkcall.data.remote.model.SignUpResponse
import com.example.safenetworkcall.data.remote.network.CallApi
import com.example.safenetworkcall.utils.Resource
import retrofit2.Response
import javax.inject.Inject

class Repository_Impl @Inject constructor(private val api : CallApi) : Repository, BaseRepository() {
    override suspend fun signUpUser(signUpUser: SignUpUser): Resource<SignUpResponse> {
        return try {
            safeApiCall { api.signUpUser(signUpUser) }
        }catch (e : Exception){
            Resource.Error(e.printStackTrace().toString())
        }
    }

    override suspend fun getAllCompanies(): Resource<Company> {
            return safeApiCall { api.getCompanies() }
        }
    }

