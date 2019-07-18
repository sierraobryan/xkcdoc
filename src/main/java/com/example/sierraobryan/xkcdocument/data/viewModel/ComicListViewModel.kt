package com.example.sierraobryan.xkcdocument.data.viewModel

import android.app.Application
import androidx.collection.ArraySet
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sierraobryan.xkcdocument.data.model.ComicTag
import com.example.sierraobryan.xkcdocument.data.model.FavoriteComic
import com.example.sierraobryan.xkcdocument.data.repository.FavoritesRepository
import com.google.android.gms.common.util.Strings

class ComicListViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritesRepository : FavoritesRepository
    internal val allFavorites: LiveData<List<FavoriteComic>>
    val comic = MutableLiveData<FavoriteComic>()
    val comicTagList : MutableLiveData<List<ComicTag>> = MutableLiveData()
    val tagList = MutableLiveData<Set<String>>()

    init {
        favoritesRepository = FavoritesRepository(application)
        allFavorites = favoritesRepository.getAll()
    }

    fun setCurrentId(comic: FavoriteComic) {
        this.comic.value = comic
    }

    // favorite functions
    fun insert(favoriteComic: FavoriteComic) {
        favoritesRepository.insert(favoriteComic)
    }

    fun delete(favoriteComic: FavoriteComic) {
        favoritesRepository.delete(favoriteComic)
    }

    fun isFavoriteFromId(comic: FavoriteComic): Boolean {
        return allFavorites.value!!.contains(comic)
    }

    // tag functions
    fun setComicTagList(list: List<ComicTag>) {
        comicTagList.value = list
        makeTagList()
    }

    private fun makeTagList() {
        val setOfTags = ArraySet<String>()
        for (comic in comicTagList.value!!) {
            setOfTags.add(comic.tag)
        }
        tagList.value = setOfTags
    }

    private fun getComicsWithTag(tag: String): List<ComicTag> {
        val comicsWithTag : MutableList<ComicTag> = ArrayList()
        for (comic in comicTagList.value!!)  {
            if (tag.equals(comic.tag)) {
                comicsWithTag.add(comic)
            }
        }
        return comicsWithTag
    }

    fun getAllTagsforId(comicId: Int) : List<String> {
        val tagsForComic: MutableSet<String> = ArraySet()
        for (comic in comicTagList.value!!)  {
            if (comicId.equals(comic.comicId)) {
                tagsForComic.add(comic.tag)
            }
        }
        return tagsForComic.toList()
    }

    fun getAllComicsForTag(tag: String) : List<ComicTag> {
        val tagsForComic: MutableSet<ComicTag> = ArraySet()
        for (comic in comicTagList.value!!)  {
            if (tag.equals(comic.tag)) {
                tagsForComic.add(comic)
            }
        }
        return tagsForComic.toList()
    }



}