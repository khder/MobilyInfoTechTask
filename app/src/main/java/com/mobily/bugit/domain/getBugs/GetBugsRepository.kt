package com.mobily.bugit.domain.getBugs

import com.mobily.bugit.domain.Resource

interface GetBugsRepository {
    suspend fun getAllBugs():Resource
}