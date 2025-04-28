package com.mobily.bugit.domain.getBugs

data class Bug(
    val id:String,
    val title:String,
    val description:String,
    val imageUrl:String,
    val date:String
)
