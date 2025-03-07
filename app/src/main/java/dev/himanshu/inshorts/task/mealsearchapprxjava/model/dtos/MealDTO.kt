package dev.himanshu.inshorts.task.mealsearchapprxjava.model.dtos

data class MealDTO(
    val idMeal: String,
    val strArea: String,
    val strCategory: String,
    val strInstructions: String,
    val strMeal: String,
    val strMealAlternate: Any,
    val strMealThumb: String,
    val strTags: String,
    val strYoutube: String
)