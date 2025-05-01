package com.mobily.bugit.data.getBugs.remote

data class SpreadSheetRemote(
    val spreadsheetId:String,
    val sheets: List<Sheet>
)
