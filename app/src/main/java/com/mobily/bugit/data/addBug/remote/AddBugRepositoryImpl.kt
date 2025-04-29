package com.mobily.bugit.data.addBug.remote

import com.mobily.bugit.common.RandomNumbersUtils
import com.mobily.bugit.data.Config
import com.mobily.bugit.data.getBugs.remote.CellData
import com.mobily.bugit.data.getBugs.remote.GetBugsApiService
import com.mobily.bugit.data.getBugs.remote.GridData
import com.mobily.bugit.data.getBugs.remote.RowData
import com.mobily.bugit.data.getBugs.remote.Sheet
import com.mobily.bugit.data.getBugs.remote.SheetProperties
import com.mobily.bugit.data.getBugs.remote.SpreadSheetRemote
import com.mobily.bugit.domain.Bug
import com.mobily.bugit.domain.Resource
import com.mobily.bugit.domain.addBug.AddBugRepository
import java.util.UUID
import javax.inject.Inject

class AddBugRepositoryImpl @Inject constructor(
    private val getBugsApiService: GetBugsApiService,
    private val addBugApiService: AddBugApiService
) : AddBugRepository {
    override suspend fun addBug(bug: Bug) :Resource {
        try {
            val bugsSheets = getBugsApiService.getAllBugs(Config.spreadsheetId)
            var isBugSheetExist = false
            for (i in 0..<bugsSheets.sheets.size) {
                if(bugsSheets.sheets[i].properties.title == bug.date){
                    isBugSheetExist = true
                    break
                }
            }
            if(isBugSheetExist){
                addBugApiService.addBugExistingSheet(
                    authKey = Config.spreadsheetTokenKey,
                    valueInputOption = "RAW",
                    spreadsheetId = Config.spreadsheetId,
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
                            arrayListOf(bug.imageUrl!!)
                        )
                    )
                )
            }else{
                addBugApiService.addBugNewSheet(
                    Config.spreadsheetApiKey,
                    SpreadSheetRemote(sheets = listOf(
                        Sheet(
                            properties = SheetProperties(
                                sheetId = RandomNumbersUtils.generateRandomNumber(
                                    from = RandomNumbersUtils.FROM_DEFAULT,
                                    to = RandomNumbersUtils.TO_DEFAULT
                                ), title = bug.date
                            ),
                            data = arrayListOf(GridData(
                                arrayListOf(RowData(
                                    arrayListOf<CellData>().apply {
                                        add(CellData(
                                            RandomNumbersUtils.generateRandomNumberAsString(
                                                RandomNumbersUtils.FROM_DEFAULT,
                                                RandomNumbersUtils.TO_DEFAULT
                                            )
                                        ))
                                        add(CellData(
                                           bug.title
                                        ))
                                        add(CellData(
                                            bug.description
                                        ))
                                        add(CellData(
                                            bug.imageUrl!!
                                        ))
                                    }
                                ))
                            ))
                        )
                    ))
                )
            }
            return Resource.Success<String>()
        }catch (e:Exception){
            return Resource.Error(e.localizedMessage)
        }

    }
}