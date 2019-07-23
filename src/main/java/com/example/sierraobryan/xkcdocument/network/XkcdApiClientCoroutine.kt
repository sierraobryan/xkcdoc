package com.example.sierraobryan.xkcdocument.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class XkcdApiClientCoroutine {

    companion object {

        private const val  BASE_URL = "https://xkcd.com/"

        fun create(): XkcdService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create(XkcdService::class.java)
        }
    }
}