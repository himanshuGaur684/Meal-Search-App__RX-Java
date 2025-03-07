package dev.himanshu.inshorts.task.mealsearchapprxjava.model

import dev.himanshu.inshorts.task.mealsearchapprxjava.model.dtos.MealResponse
import io.reactivex.rxjava3.core.Observable

class MealRepository {

    private val apiService = RetrofitInstance.getApiService()

    fun searchMeals(q: String): Observable<MealResponse> {
        return apiService.searchMeals(q)
    }

}