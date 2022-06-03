package com.example.safenetworkcall.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safenetworkcall.data.SignUpUser
import com.example.safenetworkcall.data.remote.model.Company
import com.example.safenetworkcall.data.remote.model.SignUpResponse
import com.example.safenetworkcall.data.remote.repository.Repository
import com.example.safenetworkcall.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repo : Repository) : ViewModel() {


    private val _signUpResponse: MutableLiveData<Resource<SignUpResponse>> = MutableLiveData()
    val signUpResponse: LiveData<Resource<SignUpResponse>> = _signUpResponse

    private val _listOfCompanies: MutableLiveData<Resource<Company>> = MutableLiveData()
    val listOfCompanies: LiveData<Resource<Company>> = _listOfCompanies


    fun registerSupplier(supplier: SignUpUser) {
        viewModelScope.launch {
            val response = repo.signUpUser(supplier)
            _signUpResponse.value = response
        }
    }

    fun getCompanies() {
        viewModelScope.launch {
            val response = repo.getAllCompanies()
                _listOfCompanies.postValue(response)
            }
        }
}