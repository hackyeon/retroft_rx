package com.hackyeon.retrofit_rx.api.network.forAndroid

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.hackyeon.retrofit_rx.*
import com.hackyeon.retrofit_rx.api.request.ReqGetPapgo
import com.hackyeon.retrofit_rx.api.response.ResGetPapago
import io.reactivex.Observable
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.lang.StringBuilder

class RetrofitNetworkRequest: NetworkRequest {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor{ chain ->
            var request: Request = chain.request().newBuilder()
                .addHeader(CLIENT_ID, MY_ID)
                .addHeader(CLIENT_SECRET, MY_SECRET)
                .build()
            chain.proceed(request)
        })
        .addInterceptor(HttpLoggingInterceptor(MyLogger()).apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private fun getClient(domain: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(domain)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private fun getService(client: Retrofit): APIService {
        return client.create(APIService::class.java)
    }


    override fun <T, U> requestJsonObject(requestObject: T): Observable<U> {
        return when(requestObject) {
            is ReqGetPapgo -> getService(getClient(MAIN_DOMAIN, okHttpClient)).doGetPapago(requestObject as ReqGetPapgo) as Observable<U>
            else -> throw Exception("[Network] RetrofitNotImplementedObject")
        }
    }
}

interface APIService{
    @POST(PAPAGO_URL)
    fun doGetPapago(@Body req: ReqGetPapgo): Observable<ResGetPapago>
}


class MyLogger: HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        val logName = "MyLogger"
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val sb = StringBuilder()
                sb.append("\n######################################################################\n")
                val prettyPrintJson = GsonBuilder().setPrettyPrinting()
                    .create().toJson(JsonParser().parse(message))
                sb.append(prettyPrintJson)
                sb.append("\n######################################################################")
                Log.d(logName, sb.toString())
            } catch (m: JsonSyntaxException) {
                Log.d(logName, message)
            }
        } else {
            Log.d(logName, message)
            return
        }
    }
}

