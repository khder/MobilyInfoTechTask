package com.mobily.bugit.ui.addBug

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.mobily.bugit.domain.Bug
import com.mobily.bugit.ui.utils.DateUtils
import com.mobily.bugit.ui.utils.GetCustomContents
import java.io.File
import java.util.UUID

@Composable
fun AddBugScreenContent(
    modifier: Modifier = Modifier,
    onAddBugButtonClicked:(Bug)->Unit
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
    val context = LocalContext.current
    val photoPicker = rememberLauncherForActivityResult(
        contract = GetCustomContents(),
        onResult = { uris ->
            if(uris.isNotEmpty()){
                imagePath = getUri(uris[0],context)
            }
        })

    LazyColumn(
        modifier = modifier
    ) {
        item(key = "title") {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        item(key = "description") {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        item(key = "inputImage") {
            InputImage(imagePath = imagePath, onSelectImageButtonClicked = {
                photoPicker.launch("image/*")
            })
            Spacer(modifier = Modifier.height(10.dp))
        }
        item(key = "addBug"){
            Button(onClick = {onAddBugButtonClicked(
                Bug(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    description = description,
                    date = DateUtils.getCurrentDateFormatted(),
                    imageFilePath = imagePath
                )
            )}) {
                Text(text = "Create New Bug")
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
        Button(onClick = { onSelectImageButtonClicked() }) {
            Text(text = "Select Image")
        }
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imagePath)
            .build(),
        contentDescription = "icon",
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
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

