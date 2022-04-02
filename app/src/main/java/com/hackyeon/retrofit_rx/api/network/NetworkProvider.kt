package com.hackyeon.retrofit_rx.api.network

import com.hackyeon.retrofit_rx.api.network.forAndroid.NetworkProviderAndroid
import com.hackyeon.retrofit_rx.api.network.forAndroid.RetrofitNetworkRequest
import io.reactivex.Observable

class NetworkProvider {
    
    private val provider = NetworkProviderFactory.make()

    fun <T, U> requestJsonObject(requestObject: T): Observable<U> {
        return provider.requestJsonObject(requestObject)
    }
}

interface NetworkProviding {
    fun <T, U> requestJsonObject(requestObject: T) :Observable<U>
}

object NetworkProviderFactory {
    fun make(): NetworkProviding {
        return NetworkProviderAndroid(RetrofitNetworkRequest())
    }
}