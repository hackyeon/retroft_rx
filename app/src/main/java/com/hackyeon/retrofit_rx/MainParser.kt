package com.hackyeon.retrofit_rx

import com.hackyeon.retrofit_rx.api.response.ResGetPapago

class MainParser {
    fun parsePapago(json: ResGetPapago): String? {
        return json.message?.result?.translatedText
    }
}