package com.example.sierraobryan.xkcdocument.data.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sierraobryan.xkcdocument.data.model.ApiResponse
import com.example.sierraobryan.xkcdocument.data.model.Comic
import com.example.sierraobryan.xkcdocument.data.repository.XkcdRepository
import com.example.sierraobryan.xkcdocument.network.XkcdApiClientCoroutine
import com.example.sierraobryan.xkcdocument.network.XkcdApiIClientLiveData
import com.example.sierraobryan.xkcdocument.network.XkcdService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class XkcdViewModel : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)


    var singleImage: LiveData<ApiResponse<Comic>> = MutableLiveData()
    val firstImageFromCoroutine = MutableLiveData<Comic>()
    var numberOfComics: Int = 2177

    val service : XkcdService = XkcdApiIClientLiveData.create()
    val coroutineService : XkcdService = XkcdApiClientCoroutine.create()
    val repository = XkcdRepository(service, coroutineService)


    val firstImage = repository.getHomeScreen()

    fun getFirstImage() {
        scope.launch {
            val firstImageData = repository.getHomeScreenCoroutine()
            firstImageFromCoroutine.postValue(firstImageData)
        }
    }

    fun getSpecificImage(comicId: Int) {
        singleImage = repository.getSpecificComic(comicId)
    }

    fun cancelAllRequests() = coroutineContext.cancel()


}