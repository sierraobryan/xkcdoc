package com.example.sierraobryan.xkcdocument.network

import com.example.sierraobryan.xkcdocument.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class XkcdApiIClientLiveData {

    companion object {

        fun create(): XkcdService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl(Constants.XKDC_BASE_URL)
                .build()
            return retrofit.create(XkcdService::class.java)
        }
    }
}