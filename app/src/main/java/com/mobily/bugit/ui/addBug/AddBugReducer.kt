package com.mobily.bugit.ui.addBug

import androidx.compose.runtime.Immutable
import com.mobily.bugit.domain.Bug
import com.mobily.bugit.ui.base.Reducer

class AddBugReducer:Reducer<AddBugReducer.AddBugState,AddBugReducer.AddBugEvent,AddBugReducer.AddBugEffect> {
    @Immutable
    sealed class AddBugEvent : Reducer.ViewEvent {
        data class UpdateAddBugLoading(val isLoading: Boolean) : AddBugEvent()
        data class ShowError(val error: String) : AddBugEvent()
        data class ShowSuccess(val success: String) : AddBugEvent()
    }

    @Immutable
    sealed class AddBugEffect : Reducer.ViewEffect {
        data class ShowError(val error: String) : AddBugEffect()
        data class ShowSuccess(val success: String) : AddBugEffect()
    }

    @Immutable
    data class AddBugState(
        val addBugLoading: Boolean
    ) : Reducer.ViewState {
        companion object {
            fun initial() = AddBugState(
                addBugLoading = false,
            )
        }
    }

    override fun reduce(
        previousState: AddBugState,
        event: AddBugEvent
    ): Pair<AddBugState, AddBugEffect?> {
        return when (event) {
            is AddBugEvent.UpdateAddBugLoading -> {
                previousState.copy(
                    addBugLoading = event.isLoading
                ) to null
            }

            is AddBugEvent.ShowSuccess -> {
                previousState to AddBugEffect.ShowSuccess(success = event.success)
            }

            is AddBugEvent.ShowError -> {
                previousState to AddBugEffect.ShowError(error = event.error)
            }
        }
    }
}