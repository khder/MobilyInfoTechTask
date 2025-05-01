package com.mobily.bugit.ui.addBug

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.mobily.bugit.domain.Bug
import com.mobily.bugit.ui.utils.DateUtils
import com.mobily.bugit.ui.utils.GetCustomContents
import com.mobily.bugit.ui.utils.Loading
import com.mobily.bugit.ui.utils.rememberFlowWithLifecycle
import java.io.File

@Composable
fun AddBugScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AddBugViewModel = hiltViewModel()
){
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)
    val context = LocalContext.current
    var isClearInputs by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                is AddBugReducer.AddBugEffect.ShowSuccess ->{
                    isClearInputs = true
                    Toast.makeText(context,action.success, Toast.LENGTH_LONG).show()
                    //isClearInputs = false
                }
                is AddBugReducer.AddBugEffect.ShowError -> {
                    Toast.makeText(context,action.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    AddBugScreenContent(
        modifier = modifier,
        isLoading = state.value.addBugLoading,
        isClearInputs = isClearInputs,
        onClearInputsEnding = {inIsClearInputs->
            isClearInputs =  inIsClearInputs
        },
        navHostController =navHostController,
        onAddBugButtonClicked = viewModel::addBug
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBugScreenContent(
    modifier: Modifier = Modifier,
    isLoading:Boolean,
    isClearInputs:Boolean,
    onClearInputsEnding:(Boolean)->Unit,
    navHostController: NavHostController,
    onAddBugButtonClicked:(Bug)->Unit,
){
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    var imagePath:Uri? by remember {
        mutableStateOf(null)
    }
    if(isLoading) {
        Loading()
    }
    if(isClearInputs){
        title = ""
        description = ""
        imagePath = null
        onClearInputsEnding(false)
    }
    val context = LocalContext.current
    val photoPicker = rememberLauncherForActivityResult(
        contract = GetCustomContents(),
        onResult = { uris ->
            if(uris.isNotEmpty()){
                imagePath = getUri(uris[0],context)
            }
        })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "AddNewBug")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                                    IconButton(onClick = {
                                        navHostController.navigateUp()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Arrow Back"
                                        )
                                    }
                                }
            )
        },
    ) {paddingValues->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item(key = "title") {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            item(key = "description") {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            item(key = "inputImage") {
                InputImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    imagePath = imagePath,
                    onSelectImageButtonClicked = { photoPicker.launch("image/*") }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            item(key = "addBug"){
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                    if(title.isEmpty() || description.isEmpty() || imagePath == null){
                        Toast.makeText(context,"Enter All Data",Toast.LENGTH_LONG).show()
                    }else{
                        onAddBugButtonClicked(
                            Bug(
                                id = "",
                                title = title,
                                description = description,
                                date = DateUtils.getCurrentDateFormatted(),
                                imageFilePath = imagePath
                            ))
                    }
                }) {
                    Text(text = "Create New Bug")
                }
            }
        }
    }
}

@Composable
private fun InputImage(
    modifier: Modifier = Modifier,
    onSelectImageButtonClicked : ()->Unit,
    imagePath:Uri?=null
){
    Column(
        modifier = modifier
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onSelectImageButtonClicked() }) {
            Text(text = "Select Image")
        }
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imagePath)
            .build(),
        contentDescription = "bugImage",
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
        )
    }
}

private fun getUri(uri: Uri, context: Context): Uri {
    var resultURI = uri
    if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
        val cr: ContentResolver = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        val extensionFile = mimeTypeMap.getExtensionFromMimeType(cr.getType(uri))
        val file = File.createTempFile("melifile", ".$extensionFile", context.cacheDir)
        val input = cr.openInputStream(uri)
        file.outputStream().use { stream ->
            input?.copyTo(stream)
        }
        input?.close()
        resultURI = Uri.fromFile(file)
    }
    return resultURI
}

