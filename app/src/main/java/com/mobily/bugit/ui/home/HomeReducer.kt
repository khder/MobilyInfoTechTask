package com.mobily.bugit.ui.home

import androidx.compose.runtime.Immutable
import com.mobily.bugit.domain.Bug
import com.mobily.bugit.ui.base.Reducer

class HomeReducer : Reducer<HomeReducer.HomeState,HomeReducer.HomeEvent,HomeReducer.HomeEffect> {
    @Immutable
    sealed class HomeEvent : Reducer.ViewEvent{
        data class UpdateBugsLoading (val isLoading:Boolean) : HomeEvent()
        data class UpdateBugsData(val bugs:List<Bug>) : HomeEvent()
        data class ShowError(val error: String) : HomeEvent()
        data object PressAddBugButton : HomeEvent()
    }

    @Immutable
    sealed class HomeEffect : Reducer.ViewEffect {
        data object NavigateToAddBugScreen : HomeEffect()
        data class ShowError(val error:String) : HomeEffect()
    }

    @Immutable
    data class HomeState(
        val bugsLoading : Boolean,
        val bugs:List<Bug>,
    ) : Reducer.ViewState {
        companion object{
            fun initial() = HomeState(
                bugsLoading = true,
                bugs = arrayListOf()
            )
        }
    }

    override fun reduce(previousState: HomeState, event: HomeEvent): Pair<HomeState, HomeEffect?> {
        return when(event){
            is HomeEvent.UpdateBugsLoading->{
                previousState.copy(
                    bugsLoading = event.isLoading
                ) to null
            }
            is HomeEvent.UpdateBugsData -> {
                previousState.copy(
                    bugs = event.bugs
                ) to null
            }
            HomeEvent.PressAddBugButton ->{
                previousState to HomeEffect.NavigateToAddBugScreen
            }

            is HomeEvent.ShowError -> {
                previousState to HomeEffect.ShowError(event.error)
            }
        }
    }
}