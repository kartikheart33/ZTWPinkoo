package com.ztb.pinkoo.api


import com.google.gson.JsonObject
import com.ztb.pinkoo.models.CategoryModel
import com.ztb.pinkoo.models.UserDataModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiInterface {


    @GET(ApiConst.CATEGORY)
    fun getCategorydetail(): Call<CategoryModel>

    @GET
    fun getUserPaginationList(@Url url:String):Call<UserDataModel>


    @POST(ApiConst.LOGIN)
    fun postLogin(@Body field: HashMap<String, Any>): Call<JsonObject>
    companion object {
        fun create(): ApiInterface {
            val timeoutInSeconds: Long = 60
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            httpClient.connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
            var client = httpClient
                .build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConst.BASE_URl)
                .client(client)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }


/*        fun create(accessToken: String): ApiInterface {

            if (accessToken.isEmpty()) {
                var client = OkHttpClient.Builder()
                    .build()

                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ApiConst.BASE_URl)
                    .client(client)
                    .build()

                return retrofit.create(ApiInterface::class.java)

            } else {
                val client = OkHttpClient.Builder()
                    .addInterceptor(ApiClient.OAuthInterceptor("Bearer", accessToken))
                    .build()

                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ApiConst.BASE_URl)
                    .client(client)
                    .build()

                return retrofit.create(ApiInterface::class.java)
            }
        }*/
    }
}