package com.mobily.bugit.data.addBug.remote

import com.mobily.bugit.data.getBugs.remote.SpreadSheetRemote
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AddBugApiService {
    @POST("v4/spreadsheets")
    suspend fun addBugNewSheet(@Query("key") apiKey:String,@Body spreadSheetRemote: SpreadSheetRemote)
    @POST("v4/spreadsheets/{spreadsheetId}/values/{sheetName}:append")
    suspend fun addBugExistingSheet(@Header("Authorization") authKey:String
                                    ,@Query("valueInputOption")valueInputOption:String,
                                    @Path("spreadsheetId")spreadsheetId:String,
                                    @Path("sheetName")sheetName:String,
                                    @Body sheetInputValuesRemote: SheetInputValuesRemote)

}