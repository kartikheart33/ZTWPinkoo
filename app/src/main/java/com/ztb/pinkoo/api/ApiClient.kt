package com.ztb.pinkoo.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private var retrofit: Retrofit? = null


    fun getClient(): Retrofit? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .connectTimeout(180, TimeUnit.MINUTES)   // connect timeout
            .writeTimeout(180, TimeUnit.MINUTES)     // write timeout
            .readTimeout(180, TimeUnit.MINUTES)      // read timeout
            .addInterceptor(interceptor).build()


        retrofit = Retrofit.Builder()
            .baseUrl(ApiConst.BASE_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit
    }

    class OAuthInterceptor(private val tokenType: String, private val acceessToken: String) :
        Interceptor {

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            request =
                request.newBuilder().header("Authorization", "$tokenType $acceessToken").build()

            return chain.proceed(request)
        }

    }
}