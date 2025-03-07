package dev.himanshu.inshorts.task.mealsearchapprxjava.model

import dev.himanshu.inshorts.task.mealsearchapprxjava.model.dtos.MealResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    @GET("api/json/v1/1/search.php")
    fun search(@Query("s") q: String): Observable<MealResponse>

}