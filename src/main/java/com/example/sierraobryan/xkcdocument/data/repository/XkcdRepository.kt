package com.example.sierraobryan.xkcdocument.data.repository

import androidx.lifecycle.LiveData
import com.example.sierraobryan.xkcdocument.data.model.ApiResponse
import com.example.sierraobryan.xkcdocument.data.model.Comic
import com.example.sierraobryan.xkcdocument.network.XkcdService

class XkcdRepository(var xkcdService: XkcdService){

    fun getHomeScreen(): LiveData<ApiResponse<Comic>> = xkcdService.getFirstImage()
    fun getSpecificComic(comicId : Int) = xkcdService.getSpecifcComic(comicId.toString())

}
