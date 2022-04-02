package com.hackyeon.retrofit_rx.api.request

class ReqGetPapgo {
    var source: String = "ko"
    var target: String = "en"
    var text: String = ""

    fun setData(text: String){
        this.text = text
    }
}