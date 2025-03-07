package dev.himanshu.inshorts.task.mealsearchapprxjava.viewModel

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

    private val repository: MealRepository by lazy { MealRepository() }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _query = PublishSubject.create<String>()

    private val compositeDisposable = CompositeDisposable()

    init {
        val dispose = _query
            .filter { it.isNotEmpty() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { query ->
                _uiState.update { UiState(isLoading = true) }
                repository.search(query)
                    .subscribeOn(Schedulers.io())
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe({ results ->
                _uiState.update { UiState(results = results.meals) }
            }, { error ->
                _uiState.update { UiState(error = error.localizedMessage.toString()) }
            })

        compositeDisposable.add(dispose)
    }

    fun updateQuery(query: String) {
        _query.onNext(query)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}

data class UiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val results: List<MealDTO>? = null
)
