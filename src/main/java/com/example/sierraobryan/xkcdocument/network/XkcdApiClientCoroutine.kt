package com.example.sierraobryan.xkcdocument.network

import com.example.sierraobryan.xkcdocument.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class XkcdApiClientCoroutine {

    companion object {

        fun create(): XkcdService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .baseUrl(Constants.XKDC_BASE_URL)
                    .build()
            return retrofit.create(XkcdService::class.java)
        }
    }
}