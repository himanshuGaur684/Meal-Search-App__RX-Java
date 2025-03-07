package dev.himanshu.inshorts.task.mealsearchapprxjava.model

class MealRepository {

    private val apiService: ApiService by lazy { RetrofitInstance.provideApiService() }

    fun search(q: String) = apiService.search(q)

}