package com.mobily.bugit.data.addBug.remote

import com.mobily.bugit.data.getBugs.remote.SpreadSheetRemote
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface AddBugApiService {
    @POST("v4/spreadsheets/{spreadsheetId}:batchUpdate")
    suspend fun createNewSheet(@Header("Authorization") authKey:String,
                               @Path("spreadsheetId") spreadsheetId:String,
                               @Body spreadSheetRequest: SpreadSheetRequest)
    @POST("v4/spreadsheets/{spreadsheetId}/values/{sheetName}:append")
    suspend fun addBugExistingSheet(@Header("Authorization") authKey:String,
                                    @Path("spreadsheetId")spreadsheetId:String,
                                    @Path("sheetName")sheetName:String,
                                    @Query("valueInputOption")valueInputOption:String,
                                    @Body sheetInputValuesRemote: SheetInputValuesRemote)

    @POST
    suspend fun uploadBugImage(@Url url:String,
                               @Header("Authorization") authKey:String,
                               @Query("uploadType")uploadType:String,
                               @Body imageFileRequest: RequestBody):UploadBugImageResponse

}