package com.example.safenetworkcall.data.remote.repository

import android.util.Log
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
import java.lang.StringBuilder


//sample1
abstract class BaseRepository {

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>) : Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response : Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    Resource.Success(data = response.body()!!)
                }else {
                    val errorResponse = response.errorBody()?.charStream()?.readText()
                    Resource.Error(
                        errorMessage = errorResponse.toString()
                    )
                }
            }catch (e: HttpException) {
                Resource.Error(errorMessage = e.message())
            }catch (e : IOException) {
                Resource.Error(errorMessage = "Please check your network")
            }catch (e: Exception) {
                Resource.Error(errorMessage = "Something went wrong")
            }
        }

    }

//    private fun convertErrorBody(errorBody : ResponseBody?) : SignUpResponse? {
//        return try {
//            errorBody?.source()?.let {
//                val moshiAdapter = Moshi.Builder().build().adapter(SignUpResponse::class.java)
//                moshiAdapter.fromJson(it)
//            }
//        }catch (e: Exception) {
//            null
//        }
//    }

    //sample2

//    val TAG = "SAFE_API_REQUEST"
//    suspend fun <T> safeApiRequest(call: suspend () -> Response<T>): Resource<T> {
//        val response = call.invoke()
//        if (response.isSuccessful) {
//            return Resource.Success(response.body()!!)
//        } else {
//            val responseErr = response.raw().message
//            val message = StringBuilder()
//            responseErr.let {
//                try {
//                    message.append(JSONObject(it).getString("error"))
//                } catch (e: JSONException) {
//                    Log.d(TAG, "The error message: ${e.message}")
//                }
//            }
//            Log.d(TAG, "SafeApiRequest: $message")
//            throw Exception(message.toString())
//        }
//    }


}

