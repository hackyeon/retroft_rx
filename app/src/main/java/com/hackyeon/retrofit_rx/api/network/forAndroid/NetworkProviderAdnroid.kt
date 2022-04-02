package com.hackyeon.retrofit_rx.api.network.forAndroid

import com.hackyeon.retrofit_rx.api.network.NetworkProviding
import io.reactivex.Observable

class NetworkProviderAndroid(private val networkRequest: NetworkRequest): NetworkProviding {

    override fun <T, U> requestJsonObject(requestObject: T): Observable<U> {
        return networkRequest.requestJsonObject(requestObject)
    }
}

interface NetworkRequest{
    fun <T, U> requestJsonObject(requestObject: T) : Observable<U>
}