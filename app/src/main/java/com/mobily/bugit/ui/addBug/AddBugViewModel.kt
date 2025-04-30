package com.mobily.bugit.ui.addBug

import androidx.lifecycle.viewModelScope
import com.mobily.bugit.domain.Bug
import com.mobily.bugit.domain.Resource
import com.mobily.bugit.domain.addBug.AddBugRepository
import com.mobily.bugit.ui.base.BaseViewModel
import com.mobily.bugit.ui.home.HomeReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBugViewModel @Inject constructor(
    private val addBugRepository: AddBugRepository
)
    : BaseViewModel<AddBugReducer.AddBugState,AddBugReducer.AddBugEvent,AddBugReducer.AddBugEffect>(
        initialState = AddBugReducer.AddBugState.initial(),
        reducer = AddBugReducer()
    )
{
    fun addBug(bug:Bug){
        viewModelScope.launch {
            sendEvent(AddBugReducer.AddBugEvent.UpdateAddBugLoading(isLoading = true))
            val resource = addBugRepository.addBug(bug)
            sendEvent(AddBugReducer.AddBugEvent.UpdateAddBugLoading(isLoading = false))
            if(resource is Resource.Success<*>){
                sendEventForEffect(AddBugReducer.AddBugEvent.ShowSuccess(success = "Bug Added Successfully"))
            }else{
                sendEventForEffect(AddBugReducer.AddBugEvent.ShowError((resource as Resource.Error).error))
            }
        }

    }
}