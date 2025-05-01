package com.mobily.bugit.data.addBug.remote

import android.net.Uri
import com.mobily.bugit.common.RandomNumbersUtils
import com.mobily.bugit.data.Config
import com.mobily.bugit.data.getBugs.remote.GetBugsApiService
import com.mobily.bugit.data.getBugs.remote.SheetProperties
import com.mobily.bugit.domain.Bug
import com.mobily.bugit.domain.Resource
import com.mobily.bugit.domain.addBug.AddBugRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class AddBugRepositoryImpl @Inject constructor(
    private val getBugsApiService: GetBugsApiService,
    private val addBugApiService: AddBugApiService
) : AddBugRepository {
    override suspend fun addBug(bug: Bug) :Resource {
        try {
            val bugsSheets = getBugsApiService.getAllBugs(
                spreadsheetId = Config.SPREAD_SHEET_ID,
                apiKey = Config.API_KEY,
                includeGridData = true
            )
            var isBugSheetExist = false
            for (i in 0..<bugsSheets.sheets.size) {
                if(bugsSheets.sheets[i].properties.title == bug.date){
                    isBugSheetExist = true
                    break
                }
            }
            val imageUrl = Config.GOOGLE_DRIVE_SHOW_IMAGE_URL + uploadBugImage(bug.imageFilePath!!) + "?alt=media&source=downloadUrl"

            if(!isBugSheetExist){
                addBugApiService.createNewSheet(
                    authKey = Config.ACCESS_TOKEN_KEY,
                    spreadsheetId = Config.SPREAD_SHEET_ID,
                    spreadSheetRequest = SpreadSheetRequest(
                        arrayListOf<Request>().apply {
                            add(Request(
                                AddSheetRequest(
                                    properties = SheetProperties(
                                        RandomNumbersUtils.generateRandomNumber(
                                            RandomNumbersUtils.FROM_DEFAULT,
                                            RandomNumbersUtils.TO_DEFAULT
                                        ),
                                        title = bug.date
                                    )
                                )
                            ))
                        }
                    )
                )
            }

            addBugApiService.addBugExistingSheet(
                authKey = Config.ACCESS_TOKEN_KEY,
                valueInputOption = "RAW",
                spreadsheetId = Config.SPREAD_SHEET_ID,
                sheetName = bug.date,
                sheetInputValuesRemote = SheetInputValuesRemote(
                    majorDimension = "COLUMNS",
                    values = arrayListOf(
                        arrayListOf(RandomNumbersUtils.generateRandomNumberAsString(
                            RandomNumbersUtils.FROM_DEFAULT,
                            RandomNumbersUtils.TO_DEFAULT)
                        ),
                        arrayListOf(bug.title),
                        arrayListOf(bug.description),
                        arrayListOf(imageUrl)
                    )
                )
            )


            return Resource.Success<String>()
        }catch (e:Exception){
            return Resource.Error(e.localizedMessage)
        }

    }

    override suspend fun uploadBugImage(filePath: Uri): String {
        val uploadBugImageResponse = addBugApiService.uploadBugImage(
            url = Config.GOOGLE_DRIVE_UPDATE_API_URL,
            authKey = Config.ACCESS_TOKEN_KEY,
            uploadType = Config.UPLOAD_TYPE_MEDIA,
            imageFileRequest = File(filePath.path!!).asRequestBody("image/*".toMediaTypeOrNull())
        )
        return uploadBugImageResponse.id
    }
}