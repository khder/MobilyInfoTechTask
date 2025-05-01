package com.mobily.bugit.ui.home

import androidx.lifecycle.viewModelScope
import com.mobily.bugit.domain.Resource
import com.mobily.bugit.domain.Bug
import com.mobily.bugit.domain.getBugs.GetBugsRepository
import com.mobily.bugit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBugsRepository: GetBugsRepository
):BaseViewModel<HomeReducer.HomeState,HomeReducer.HomeEvent,HomeReducer.HomeEffect>(
    initialState = HomeReducer.HomeState.initial(),
    reducer = HomeReducer()
) {
    suspend fun loadAllBugs(){
        viewModelScope.launch {
            sendEvent(HomeReducer.HomeEvent.UpdateBugsLoading(isLoading = true))
            val resource = getBugsRepository.getAllBugs()
            sendEvent(HomeReducer.HomeEvent.UpdateBugsLoading(isLoading = false))
            if(resource is Resource.Success<*>){
                sendEvent(HomeReducer.HomeEvent.UpdateBugsData(resource.data as List<Bug>))
            }else{
                sendEventForEffect(HomeReducer.HomeEvent.ShowError((resource as Resource.Error).error))
            }
        }
    }
}