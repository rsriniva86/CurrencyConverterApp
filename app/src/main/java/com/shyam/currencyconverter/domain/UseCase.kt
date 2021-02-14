package com.shyam.currencyconverter.domain

abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue> {

    var requestValues: Q? = null

    var useCaseCallback: UseCaseCallback<P>? = null


    abstract suspend fun executeUseCase(requestValues: Q?)

    /**
     * Data passed to a request.
     */
    interface RequestValues

    /**
     * Data received from a request.
     */
    interface ResponseValue

    interface UseCaseCallback<P> {
        fun onSuccess(response: P)
        fun onError(t: Throwable)
    }

}