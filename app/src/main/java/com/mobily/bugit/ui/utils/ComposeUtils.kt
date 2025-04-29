package com.mobily.bugit.ui.utils

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}

@Composable
fun Loading(
    modifier: Modifier = Modifier,
    isShowLoading:Boolean
){
    if(isShowLoading){
        CircularProgressIndicator(modifier = modifier)
    }
}

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    url:String,
    contentDescription:String
){
    AsyncImage(
        modifier = modifier,
        model = url,
        contentDescription = contentDescription
    )
}


