package com.example.sierraobryan.xkcdocument.data.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.sierraobryan.xkcdocument.data.dao.FavoriteDao
import com.example.sierraobryan.xkcdocument.data.database.FavoritesDatabase
import com.example.sierraobryan.xkcdocument.data.model.FavoriteComic

class FavoritesRepository(application: Application) {

    private val dao: FavoriteDao
    private val data: LiveData<List<FavoriteComic>>

    init {
        val db = FavoritesDatabase.getInstance(application)
        dao = db?.favoriteDao()!!
        data = dao.getAllFavorites()
    }

    fun getAll(): LiveData<List<FavoriteComic>> {
        return data
    }

    fun insert(favoriteComic: FavoriteComic) {
        insertAsyncTask(dao).execute(favoriteComic)
    }

    fun delete(favoriteComic: FavoriteComic) {
        deleteAsyncTask(dao).execute(favoriteComic)
    }

    private class insertAsyncTask internal constructor(private val dao: FavoriteDao) :
            AsyncTask<FavoriteComic, Void, Void>() {
        override fun doInBackground(vararg params: FavoriteComic): Void? {
            dao.insert(params[0])
            return null
        }
    }

    private class deleteAsyncTask internal constructor(private val dao: FavoriteDao) :
            AsyncTask<FavoriteComic, Void, Void>() {
        override fun doInBackground(vararg params: FavoriteComic): Void? {
            dao.delete(params[0])
            return null
        }
    }

}