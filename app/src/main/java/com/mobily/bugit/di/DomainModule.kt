package com.mobily.bugit.di

import com.mobily.bugit.data.getBugs.remote.GetBugsRepositoryImpl
import com.mobily.bugit.domain.getBugs.GetBugsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class GetBugsModule {
    @Binds
    abstract fun bindGetBugsRepository(
        getBugsRepository: GetBugsRepositoryImpl
    ): GetBugsRepository
}