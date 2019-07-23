package com.example.sierraobryan.xkcdocument.data.viewModel

import android.app.Application
import androidx.collection.ArraySet
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sierraobryan.xkcdocument.data.model.Comic
import com.example.sierraobryan.xkcdocument.data.model.ComicWithTag
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
import com.example.sierraobryan.xkcdocument.data.repository.HistoryRepository

class ComicListViewModel(application: Application) : AndroidViewModel(application) {

    private val historyRepository : HistoryRepository
    internal val allHistory: LiveData<List<ComicWithFavorite>>
    internal val allFavorites: LiveData<List<ComicWithFavorite>>
    val comic = MutableLiveData<ComicWithFavorite>()
    val comicWithTagList : MutableLiveData<List<ComicWithTag>> = MutableLiveData()
    val tagList = MutableLiveData<Set<String>>()

    init {
        historyRepository = HistoryRepository(application)
        allHistory = historyRepository.getAll()
        allFavorites = historyRepository.getAllFavorites()
    }

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

    fun isFavoriteFromId(id : Int): Boolean {
        if (!allFavorites.value.isNullOrEmpty()) {
            return allFavorites.value!!.any { comic -> comic.num == id }
        }
        return false
    }

    // tag functions
    fun setComicTagList(list: List<ComicWithTag>) {
        comicWithTagList.value = list
        makeTagList()
    }

    private fun makeTagList() {
        val setOfTags = ArraySet<String>()
        for (comic in comicWithTagList.value!!) {
            setOfTags.add(comic.tag)
        }
        tagList.value = setOfTags
    }

    private fun getComicsWithTag(tag: String): List<ComicWithTag> {
        val comicsWithTags : MutableList<ComicWithTag> = ArrayList()
        for (comic in comicWithTagList.value!!)  {
            if (tag == comic.tag) {
                comicsWithTags.add(comic)
            }
        }
        return comicsWithTags
    }

    fun getAllTagsForId(comicId: Int) : List<String> {
        val tagsForComic: MutableSet<String> = ArraySet()
        for (comic in comicWithTagList.value!!)  {
            if (comicId == comic.num) {
                tagsForComic.add(comic.tag)
            }
        }
        return tagsForComic.toList()
    }

    fun getAllComicsForTag(tag: String) : List<Comic> {
        val tagsForComic: MutableSet<Comic> = ArraySet()
        for (comic in comicWithTagList.value!!)  {
            if (tag == comic.tag) {
                tagsForComic.add(comic.toComic())
            }
        }
        return tagsForComic.toList()
    }



}