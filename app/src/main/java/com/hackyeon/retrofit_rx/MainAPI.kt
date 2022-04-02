package com.hackyeon.retrofit_rx

import com.hackyeon.retrofit_rx.api.API
import com.hackyeon.retrofit_rx.api.request.ReqGetPapgo
import com.hackyeon.retrofit_rx.api.response.ResGetPapago
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MainAPI {

    fun GetPapago(
        text: String
    ): Single<String?> {
        return Observable.just(text)
            .observeOn(Schedulers.io())
            .map {
                ReqGetPapgo().apply {
                    setData(it)
                }
            }
            .flatMap {
                API.request<ReqGetPapgo, ResGetPapago>(it)
            }
            .filter{
               if(!it.message?.result?.translatedText.isNullOrEmpty()) {
                   true
               } else {
                   throw Exception("Error: ${it.message?.result?.translatedText}")
               }
            }
            .map {
                MainParser().parsePapago(it)
            }
            .singleOrError()
    }


}