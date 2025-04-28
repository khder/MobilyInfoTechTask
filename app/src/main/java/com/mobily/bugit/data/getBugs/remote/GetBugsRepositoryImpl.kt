package com.mobily.bugit.data.getBugs.remote

import com.mobily.bugit.data.Config
import com.mobily.bugit.domain.Resource
import com.mobily.bugit.domain.getBugs.Bug
import com.mobily.bugit.domain.getBugs.GetBugsRepository
import javax.inject.Inject

class GetBugsRepositoryImpl @Inject constructor(
    val getBugsApiService: GetBugsApiService
) : GetBugsRepository {

    override suspend fun getAllBugs(): Resource {
        return try {
            Resource.Success(
                BugsMapper.mapToBugs(getBugsApiService.getAllBugs(Config.spreadsheetId))
            )
        }catch (e:Exception){
            Resource.Error(e.localizedMessage)
        }
    }
}