package com.example.sierraobryan.xkcdocument.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class XkcdApiIClient {

    companion object {

        private const val  BASE_URL = "https://xkcd.com/"

        fun create(): XkcdService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(XkcdService::class.java)
        }
    }
}