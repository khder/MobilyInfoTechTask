package com.mobily.bugit.domain

import android.net.Uri

data class Bug(
    val id:String,
    val title:String,
    val description:String,
    val imageUrl:String?=null,
    val date:String,
    val imageFilePath:Uri?=null
)
