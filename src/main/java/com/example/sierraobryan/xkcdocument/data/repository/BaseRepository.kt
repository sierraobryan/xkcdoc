package com.example.sierraobryan.xkcdocument.data.repository

import android.util.Log
import com.example.sierraobryan.xkcdocument.data.model.ApiErrorResponse
import com.example.sierraobryan.xkcdocument.data.model.ApiResponse
import com.example.sierraobryan.xkcdocument.data.model.ApiSuccessResponse
import retrofit2.Response

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result : ApiResponse<T> = safeApiResult(call)
        var data : T? = null

        when(result) {
            is ApiSuccessResponse ->
                data = result.body
            is ApiErrorResponse -> {
                Log.d("1.DataRepository", "$errorMessage & Exception - ${result.errorMessage}")
            }
        }


        return data

    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>) : ApiResponse<T> {
        val response = call.invoke()
        if(response.isSuccessful) return ApiSuccessResponse(response.body()!!)

        return ApiErrorResponse(response.code(), response.errorBody()?.string()?:response.message())
    }
}