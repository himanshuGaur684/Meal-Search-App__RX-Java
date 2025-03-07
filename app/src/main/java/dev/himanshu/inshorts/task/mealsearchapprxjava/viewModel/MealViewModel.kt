package dev.himanshu.inshorts.task.mealsearchapprxjava.viewModel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dev.himanshu.inshorts.task.mealsearchapprxjava.model.MealRepository
import dev.himanshu.inshorts.task.mealsearchapprxjava.model.dtos.MealDTO
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.TimeUnit

class MealViewModel : ViewModel() {

    private val repository = MealRepository()

    private val disposable = CompositeDisposable()

    private val _query = PublishSubject.create<String>()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        val dispose = _query
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter { it.isNotEmpty() }
            .switchMap { query ->
                _uiState.update { it.copy(isLoading = true) }
                repository.searchMeals(query)
                    .map { it.meals }
                    .subscribeOn(Schedulers.io())
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { meals ->
                    _uiState.update { it.copy(isLoading = false, results = meals) }
                }, { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            results = null,
                            error = error.localizedMessage.toString()
                        )
                    }
                }
            )
        disposable.add(dispose)
    }

    fun updateQuery(query: String) {
        _query.onNext(query)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}

@Stable
data class UiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val results: List<MealDTO>? = null
)