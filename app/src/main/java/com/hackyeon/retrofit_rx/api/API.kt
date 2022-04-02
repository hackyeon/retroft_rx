package com.hackyeon.retrofit_rx.api

import com.hackyeon.retrofit_rx.api.network.NetworkProvider
import com.hackyeon.retrofit_rx.api.network.NetworkProviderFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class API {

    companion object{
        fun <T, U> request(requestObject: T): Observable<U> {
            return Observable.just(requestObject)
                .observeOn(Schedulers.io())
                .flatMap { NetworkProvider().requestJsonObject<T, U>(it) }
        }
    }

}