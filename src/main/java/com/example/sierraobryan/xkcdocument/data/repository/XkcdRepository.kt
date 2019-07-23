package com.example.sierraobryan.xkcdocument.data.repository

import androidx.lifecycle.LiveData
import com.example.sierraobryan.xkcdocument.data.model.ApiResponse
import com.example.sierraobryan.xkcdocument.data.model.Comic
import com.example.sierraobryan.xkcdocument.network.XkcdService

class XkcdRepository(var xkcdServiceLiveData: XkcdService, var xkcdServiceCoroutine: XkcdService) : BaseRepository() {

    fun getHomeScreen(): LiveData<ApiResponse<Comic>> = xkcdServiceLiveData.getFirstImage()

    suspend fun getHomeScreenCoroutine() : Comic? {
        val comicResponse = safeApiCall(
                call = {xkcdServiceCoroutine.getFirstImageCoroutine().await()},
                errorMessage = "Oops, couldn't find image"
        )
        return comicResponse
    }


    fun getSpecificComic(comicId : Int) = xkcdServiceLiveData.getSpecifcComic(comicId.toString())

}
