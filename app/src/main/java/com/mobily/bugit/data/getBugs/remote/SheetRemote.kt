package com.mobily.bugit.data.getBugs.remote

data class Sheet(
    val properties:SheetProperties,
    val data: List<GridData>
)

data class SheetProperties(val sheetId:Int,val title:String)
