package com.example.sierraobryan.xkcdocument.data.viewModel

import androidx.collection.ArraySet
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sierraobryan.xkcdocument.data.model.ComicTag
import com.google.android.gms.common.util.Strings

class ComicListViewModel : ViewModel() {

    val comicTagList : MutableLiveData<List<ComicTag>> = MutableLiveData()
    val tagList = MutableLiveData<Set<String>>()

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