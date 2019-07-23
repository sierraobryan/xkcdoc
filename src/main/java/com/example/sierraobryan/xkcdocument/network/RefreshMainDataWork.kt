package com.example.sierraobryan.xkcdocument.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.sierraobryan.xkcdocument.data.repository.XkcdRepository
import java.lang.Error

class RefreshMainDataWork(context: Context, params: WorkerParameters) :
            CoroutineWorker(context, params) {

        /**
         * Refresh the title from the network using [TitleRepository]
         *
         * WorkManager will call this method from a background thread. It may be called even
         * after our app has been terminated by the operating system, in which case [WorkManager] will
         * start just enough to run this [Worker].
         */
        override suspend fun doWork(): Result {
            val serviceCo = XkcdApiClientCoroutine.create()
            val serviceLV = XkcdApiIClientLiveData.create()
            val repository = XkcdRepository(serviceLV, serviceCo)

            return try {
                repository.getHomeScreenCoroutine()
                Result.success()
            } catch (error: Error) {
                Result.failure()
            }
        }
}

