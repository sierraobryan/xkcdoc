package com.example.sierraobryan.xkcdocument.data.viewModel

import android.app.Application
import androidx.collection.ArraySet
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sierraobryan.xkcdocument.data.model.ComicWithTag
import com.example.sierraobryan.xkcdocument.data.model.ComicShort
import com.example.sierraobryan.xkcdocument.data.repository.FavoritesRepository

class ComicListViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritesRepository : FavoritesRepository
    internal val allFavorites: LiveData<List<ComicShort>>
    val comic = MutableLiveData<ComicShort>()
    val comicWithTagList : MutableLiveData<List<ComicWithTag>> = MutableLiveData()
    val tagList = MutableLiveData<Set<String>>()

    init {
        favoritesRepository = FavoritesRepository(application)
        allFavorites = favoritesRepository.getAll()
    }

    fun setCurrentId(comicShort: ComicShort) {
        this.comic.value = comicShort
    }

    // favorite functions
    fun insert(comicShort: ComicShort) {
        favoritesRepository.insert(comicShort)
    }

    fun delete(comicShort: ComicShort) {
        favoritesRepository.delete(comicShort)
    }

    fun isFavoriteFromId(comicShort: ComicShort): Boolean {
        return allFavorites.value!!.contains(comicShort)
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

    fun getAllTagsforId(comicId: Int) : List<String> {
        val tagsForComic: MutableSet<String> = ArraySet()
        for (comic in comicWithTagList.value!!)  {
            if (comicId == comic.comicId) {
                tagsForComic.add(comic.tag)
            }
        }
        return tagsForComic.toList()
    }

    fun getAllComicsForTag(tag: String) : List<ComicShort> {
        val tagsForComic: MutableSet<ComicShort> = ArraySet()
        for (comic in comicWithTagList.value!!)  {
            if (tag == comic.tag) {
                tagsForComic.add(comic.toComicShort())
            }
        }
        return tagsForComic.toList()
    }



}