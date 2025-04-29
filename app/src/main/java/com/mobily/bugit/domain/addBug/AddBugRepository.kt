package com.mobily.bugit.domain.addBug

import com.mobily.bugit.domain.Bug
import com.mobily.bugit.domain.Resource

interface AddBugRepository {
    suspend fun addBug(bug: Bug) : Resource
}