package dev.himanshu.inshorts.task.mealsearchapprxjava.model

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata
    private fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    fun getApiService(): ApiService {
        return getInstance().create(ApiService::class.java)
    }

}