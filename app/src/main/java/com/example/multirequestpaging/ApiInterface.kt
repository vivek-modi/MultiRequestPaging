package com.example.multirequestpaging

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @GET("movie")
    fun getMovieResponse(@Query("min") min: Int): MovieResponse

    @GET("movie")
    fun getMovieResponseBefore(
        @Query("count") count: Int,
        @Query("before") before: String
    ): MovieResponse

    companion object {

        var BASE_URL = "http://www.vivek.com/"
        fun create(): ApiInterface {

            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val clientSetup = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                .readTimeout(1, TimeUnit.MINUTES) // read timeout
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .client(clientSetup)
                .addConverterFactory(MoshiConverterFactory.create(moshi))

                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}