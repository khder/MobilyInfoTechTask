package com.mobily.bugit.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mobily.bugit.domain.Bug
import com.mobily.bugit.ui.Screen
import com.mobily.bugit.ui.utils.ImageLoader
import com.mobily.bugit.ui.utils.Loading
import com.mobily.bugit.ui.utils.rememberFlowWithLifecycle

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
){
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)
    val context = LocalContext.current
    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                HomeReducer.HomeEffect.NavigateToAddBugScreen -> {
                    // This effect would result in a navigation to another screen of the application
                    // with the topicId as a parameter.
                    navController.navigate(route = Screen.AddBugScreen.route)
                }

                is HomeReducer.HomeEffect.ShowError -> {
                    Toast.makeText(context,action.error,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    HomeScreenContent(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        bugs = state.value.bugs,
        bugsLoading = state.value.bugsLoading,
        onAddBugButtonClicked = {
            viewModel.sendEventForEffect(HomeReducer.HomeEvent.PressAddBugButton)
        }
    )
}


@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    bugs:List<Bug>,
    bugsLoading:Boolean,
    onAddBugButtonClicked:()->Unit
){
    Loading(isShowLoading = bugsLoading)

    if(bugs.isNotEmpty()) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(items = bugs, key = { bug -> bug.id }) { bug ->
                    BugRow(
                        modifier = modifier,
                        bug = bug
                    )
                }
            }
        }else{
            Text(
                text = "Empty Bugs",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
    }

    SmallFloatingActionButton(
        onClick = {onAddBugButtonClicked()},
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Small floating action button.")
    }

}

@Composable
private fun BugRow(
    modifier: Modifier = Modifier,
    bug: Bug
){
    Card(modifier = modifier) {
        ImageLoader(
            url = bug.imageUrl!!,
            contentDescription = bug.description
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = bug.title,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = bug.description,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = bug.date,
        )
    }
}