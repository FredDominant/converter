package com.freddominant.converter.network

import com.freddominant.converter.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkFactory {

    companion object {
        const val BASE_URL = "https://revolut.duckdns.org/"
    }

    private val retrofitClient: Retrofit? = null

    fun getRetrofit(): Retrofit {
        return this.retrofitClient?.let {
            return it
        } ?: this.createRetrofitClient()
    }

    private fun createRetrofitClient(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        val client = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(loggingInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client.build())
            .build()
    }
}