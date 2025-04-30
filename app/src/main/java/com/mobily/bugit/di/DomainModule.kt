package com.mobily.bugit.di

import com.mobily.bugit.data.addBug.remote.AddBugRepositoryImpl
import com.mobily.bugit.data.getBugs.remote.GetBugsRepositoryImpl
import com.mobily.bugit.domain.addBug.AddBugRepository
import com.mobily.bugit.domain.getBugs.GetBugsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class BugsModule {
    @Binds
    abstract fun bindGetBugsRepository(
        getBugsRepository: GetBugsRepositoryImpl
    ): GetBugsRepository

    @Binds
    abstract fun bindAddBugsRepository(
        addBugRepositoryImpl: AddBugRepositoryImpl
    ): AddBugRepository
}