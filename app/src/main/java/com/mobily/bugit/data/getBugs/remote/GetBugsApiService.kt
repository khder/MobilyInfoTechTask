package com.mobily.bugit.data.getBugs.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetBugsApiService {
    @GET("v4/spreadsheets/{spreadsheetId}")
    suspend fun getAllBugs(@Path("spreadsheetId") spreadsheetId:String,
                           @Query("key")apiKey:String,
                           @Query("includeGridData")includeGridData:Boolean) : SpreadSheetRemote

}