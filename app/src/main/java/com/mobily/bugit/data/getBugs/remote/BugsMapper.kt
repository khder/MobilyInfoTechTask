package com.mobily.bugit.data.getBugs.remote

import com.mobily.bugit.domain.getBugs.Bug
import java.util.UUID

object BugsMapper {
    fun mapToBugs(spreadsheet: SpreadSheetRemote):List<Bug>{
        return arrayListOf<Bug>().apply {
            spreadsheet.sheets.forEach {sheet->
                sheet.data.forEach{gridData->
                   gridData.rowData.forEach { rowData ->
                       add(Bug(
                           id = UUID.randomUUID().toString(),
                           title = rowData.values[0].formattedValue,
                           description = rowData.values[1].formattedValue,
                           imageUrl = rowData.values[2].formattedValue,
                           date = sheet.properties.title
                       ))
                   }
                }
            }
        }

    }
}