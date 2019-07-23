package com.example.sierraobryan.xkcdocument.data.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sierraobryan.xkcdocument.data.model.ApiResponse
import com.example.sierraobryan.xkcdocument.data.model.Comic
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
import com.example.sierraobryan.xkcdocument.data.repository.HistoryRepository
import com.example.sierraobryan.xkcdocument.data.repository.XkcdRepository
import com.example.sierraobryan.xkcdocument.network.XkcdApiClientCoroutine
import com.example.sierraobryan.xkcdocument.network.XkcdApiIClientLiveData
import com.example.sierraobryan.xkcdocument.network.XkcdService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class XkcdViewModel(application: Application) : AndroidViewModel(application) {

    private val historyRepository : HistoryRepository
    internal val allHistory: LiveData<List<ComicWithFavorite>>
    internal val allFavorites: LiveData<List<ComicWithFavorite>>
    val comic = MutableLiveData<ComicWithFavorite>()

    var singleImage: LiveData<ApiResponse<Comic>> = MutableLiveData()
    var imageFromId = MutableLiveData<Comic>()
    val firstImageFromCoroutine = MutableLiveData<Comic>()
    var numberOfComics: Int = 2177

    val service : XkcdService = XkcdApiIClientLiveData.create()
    val coroutineService : XkcdService = XkcdApiClientCoroutine.create()
    val repository = XkcdRepository(service, coroutineService)

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)


    init {
        historyRepository = HistoryRepository(application)
        allHistory = historyRepository.getAll()
        allFavorites = historyRepository.getAllFavorites()
    }

    val firstImage = repository.getHomeScreen()

    fun getSpecificImage(comicId: Int) {
        singleImage = repository.getSpecificComic(comicId)
    }

    fun getFirstImage() {
        scope.launch {
            val firstImageData = repository.getHomeScreenCoroutine()
            insertOrUpdate(firstImageData!!.toComicWithFavorite())
            firstImageFromCoroutine.postValue(firstImageData)
        }
    }

    fun getSpecificImageCo(comicId: Int) {
        scope.launch {
            val specificImage = repository.getComicByIdCo(comicId)
            insertOrUpdate(specificImage!!.toComicWithFavorite())
            imageFromId.postValue(specificImage)
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()

    fun setCurrentComic(comicWithFavorite: ComicWithFavorite) {
        this.comic.value = comicWithFavorite
    }

    // favorite functions
    fun insertOrUpdate(comicWithFavorite: ComicWithFavorite) {
        historyRepository.insertOrUpdate(comicWithFavorite)
    }

    fun delete(comicWithFavorite: ComicWithFavorite) {
        historyRepository.delete(comicWithFavorite)
    }

    fun inHistoryFromId(id : Int): Boolean {
        if (!allHistory.value.isNullOrEmpty()) {
            return allHistory.value!!.any { comic -> comic.num == id }
        }
        return false
    }

    fun isFavoriteFromId(id : Int): Boolean {
        if (!allFavorites.value.isNullOrEmpty()) {
            return allFavorites.value!!.any { comic -> comic.num == id }
        }
        return false
    }


}