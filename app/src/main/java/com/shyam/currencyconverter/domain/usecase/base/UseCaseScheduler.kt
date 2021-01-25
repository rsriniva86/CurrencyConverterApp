package com.shyam.currencyconverter.domain.usecase.base

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


class UseCaseScheduler private constructor() {
    /* Stores handler for running on UI-Thread */
    private val handler: Handler
    init {
        handler = Handler(Looper.getMainLooper())
    }
    /**
     * Prepares a given use case and then executes it on a new Thread.
     *
     * @param useCase [UseCase]
     * @param request Request data
     * @param callback [UseCaseCallback]
     * @param <T> Request data type
     * @param <V> Response data type
    </V></T> */
    suspend fun <T, V> execute(
        useCase: UseCase<UseCase.RequestValues, UseCase.ResponseValue>,
        request: UseCase.RequestValues,
        callback: UseCase.UseCaseCallback<UseCase.ResponseValue>
    ) {
        useCase.useCaseCallback=
            UseCaseCallbackonUI(
                handler,
                callback
            )
        getExecutor()!!.execute {
        }
    }

    /**
     * Attempts to shutdown all actively running tasks and will prevent
     * any pending tasks from executing.
     */
    fun shutdownExecution() {
        if (executor != null) {
            executor!!.shutdownNow()
            executor = null
        }
    }

    /**
     * Class to ensure callback happens on UI thread
     */
    internal class UseCaseCallbackonUI<P: UseCase.ResponseValue> internal constructor(
        private val handler: Handler,
        private val callback: UseCase.UseCaseCallback<P>
    ): UseCase.UseCaseCallback<P>{
        override fun onSuccess(response: P) {
            handler.post { callback.onSuccess(response) }

        }

        override fun onError(t: Throwable) {
            handler.post { callback.onError(t) }
        }

    }



    companion object {
        /* Stores singleton instance of scheduler */
        @Volatile
        var instance: UseCaseScheduler? = null
            get() {
                if (field == null) {
                    synchronized(UseCaseScheduler::class) {
                        if (field == null) {
                            field =
                                UseCaseScheduler()
                        }
                    }
                }
                return field
            }
            private set

        /* Stores reference to thread-pool executor that's lazy loaded */
        private var executor: ThreadPoolExecutor? = null

        /**
         * Lazy-loads an instance of a thread-pool executor.
         * @return [ThreadPoolExecutor]
         */
        private fun getExecutor(): ThreadPoolExecutor? {
            if (executor == null) {
                val SIZE = 2
                val MAX = 4
                val TIMEOUT = 30
                executor = ThreadPoolExecutor(
                    SIZE, MAX, TIMEOUT.toLong(), TimeUnit.SECONDS,
                    ArrayBlockingQueue(MAX)
                )
            }
            return executor
        }
    }


}