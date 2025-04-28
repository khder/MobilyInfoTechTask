package com.mobily.bugit.data.getBugs.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface GetBugsApiService {
    @GET("v4/spreadsheets/{spreadsheetId}")
    suspend fun getAllBugs(@Path("spreadsheetId") spreadsheetId:String) : SpreadSheetRemote

}