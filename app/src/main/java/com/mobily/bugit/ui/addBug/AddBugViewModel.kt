package com.mobily.bugit.ui.addBug

import com.mobily.bugit.domain.Bug
import com.mobily.bugit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddBugViewModel @Inject constructor()
    : BaseViewModel<AddBugReducer.AddBugState,AddBugReducer.AddBugEvent,AddBugReducer.AddBugEffect>(
        initialState = AddBugReducer.AddBugState.initial(),
        reducer = AddBugReducer()
    )
{
    fun addBug(bug:Bug){
        sendEvent(AddBugReducer.AddBugEvent.UpdateAddBugLoading(isLoading = true))

    }
}