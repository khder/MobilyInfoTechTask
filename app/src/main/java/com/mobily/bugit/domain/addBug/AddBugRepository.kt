package com.mobily.bugit.domain.addBug

import android.net.Uri
import com.mobily.bugit.domain.Bug
import com.mobily.bugit.domain.Resource
import java.io.File

interface AddBugRepository {
    suspend fun addBug(bug: Bug) : Resource
    suspend fun uploadBugImage(filePath:Uri) : String
}