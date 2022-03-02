package com.quadtric.renewash.apiRelatedFiles

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Suppress("SpellCheckingInspection")
class ApiClient {
    companion object {
        private const val BASE_URL = " http://mybestbot.com/" //Live Url
        private var retrofit: Retrofit? = null
        fun getClient(): Retrofit? {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient.Builder = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
            client.addInterceptor(Interceptor { chain ->
                var request = chain.request()
                request = request
                    .newBuilder()
                    .build()
                chain.proceed(request)
            })

            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit
        }
    }
}