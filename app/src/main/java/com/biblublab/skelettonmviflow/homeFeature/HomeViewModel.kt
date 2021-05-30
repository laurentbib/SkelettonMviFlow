package com.biblublab.skelettonmviflow.homeFeature

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biblublab.domain.homeFeature.HomeUseCase
import com.biblublab.domain.homeFeature.entities.HomeNews
import com.biblublab.skelettonmviflow.common.error.UiFailure
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val pendingAction = MutableSharedFlow<HomeAction>()

    private val _uiEffects = MutableSharedFlow<HomeEffect>(replay = 0)
    val uiEffects: Flow<HomeEffect>
        get() = _uiEffects.asSharedFlow()


    private val _homeState = MutableStateFlow(HomeState(HomeStatus.NotFetched, emptyList()))
    private var currentState = homeState.value
    val homeState : StateFlow<HomeState>
        get() = _homeState

    init {
        viewModelScope.launch {
            pendingAction.collect {action ->
                when(action){
                    HomeAction.FetchNews -> {
                        reduce(currentState.copy(homeStatus = HomeStatus.Fetching))
                        fetchNews()
                    }
                    is HomeAction.OnClickItem -> openDetails(action.itemView, action.news)
                }
            }
        }
    }

    internal fun processAction(action : HomeAction){
        viewModelScope.launch {
            pendingAction.emit(action)
        }
    }

    private fun reduce(newState : HomeState){
        _homeState.value = newState
    }

    private fun openDetails(itemView: View, news: HomeNews) {
        viewModelScope.launch {
            _uiEffects.emit(HomeEffect.OpenDetail(itemView, news))
        }
    }

    private fun fetchNews(){
        viewModelScope.launch {
            homeUseCase.invoke(Unit).collect { it ->
                it.either({failure ->
                    reduce(currentState.copy(homeStatus = HomeStatus.Error(UiFailure.fromDomain(failure).resLabel)))
                },{newsList ->
                    reduce(currentState.copy(homeStatus = HomeStatus.Fetched, newsList = newsList.map { news ->HomeItemView(news) }))
                })
            }
        }
    }
}