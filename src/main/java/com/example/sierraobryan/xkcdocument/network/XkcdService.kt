package com.example.sierraobryan.xkcdocument.network

import androidx.lifecycle.LiveData
import com.example.sierraobryan.xkcdocument.data.model.ApiResponse
import com.example.sierraobryan.xkcdocument.data.model.Comic
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface XkcdService {

    @GET("info.0.json")
    fun getFirstImageCoroutine() : Deferred<Response<Comic>>

    @GET("{comic_id}/info.0.json")
    fun getSpecifcComicCo(@Path(value = "comic_id") comicId : String) : Deferred<Response<Comic>>

    @GET("info.0.json")
    fun getFirstImage() : LiveData<ApiResponse<Comic>>

    @GET("{comic_id}/info.0.json")
    fun getSpecifcComic(@Path(value = "comic_id") comicId : String) : LiveData<ApiResponse<Comic>>

}