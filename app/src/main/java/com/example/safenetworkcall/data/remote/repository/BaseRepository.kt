package com.example.safenetworkcall.data.remote.repository

import android.util.Log
import com.example.safenetworkcall.data.remote.model.Company
import com.example.safenetworkcall.data.remote.model.SignUpResponse
import com.example.safenetworkcall.utils.Resource
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException




abstract class BaseRepository {

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>) : Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response : Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    Resource.Success(data = response.body()!!)
                }else {
                    val errorResponse = convertErrorBody(response.errorBody())
                    Resource.Error(
                        errorMessage = errorResponse?.toString() ?: "Unsuccessful. Try again"
                    )
                }
            }catch (e: HttpException) {
                Resource.Error(errorMessage = e.message())
            }catch (e : IOException) {
                Resource.Error(errorMessage = "Please check your network connection")
            }catch (e: Exception) {
                Resource.Error(errorMessage = "Something went wrong")
            }
        }

    }

    private fun convertErrorBody(errorBody : ResponseBody?) : SignUpResponse? {
        return try {
            errorBody?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(SignUpResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        }catch (e: Exception) {
            null
        }
    }
}

