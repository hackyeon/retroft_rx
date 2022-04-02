package com.hackyeon.retrofit_rx.api.response

class ResGetPapago {
    var message: Message? = Message()
}

class Message {
    var result: Result? = Result()
}

class Result(
    var translatedText: String? = ""
)