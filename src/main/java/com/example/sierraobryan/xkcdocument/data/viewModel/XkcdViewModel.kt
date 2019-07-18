package com.example.sierraobryan.xkcdocument.data.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sierraobryan.xkcdocument.data.model.ApiResponse
import com.example.sierraobryan.xkcdocument.data.model.Comic
import com.example.sierraobryan.xkcdocument.data.repository.XkcdRepository
import com.example.sierraobryan.xkcdocument.network.XkcdApiIClient

class XkcdViewModel : ViewModel() {

    var singleImage: LiveData<ApiResponse<Comic>> = MutableLiveData()

    val service = XkcdApiIClient.create()
    val repository = XkcdRepository(service)
    val firstImage = repository.getHomeScreen()

    fun getSpecificImage(comicId: Int) {
        singleImage = repository.getSpecificComic(comicId)
    }


}