package com.hu.bugit.domain.di

import com.hu.bugit.domain.repository.bugForm.BugFormRepository
import com.hu.bugit.domain.repository.bugForm.ImageRepository
import com.hu.bugit.domain.repository.home.HomeRepository
import com.hu.bugit.domain.usecase.bugForm.SubmitBugUseCase
import com.hu.bugit.domain.usecase.bugForm.UploadImageUseCase
import com.hu.bugit.domain.usecase.home.BugListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing domain-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideUploadImageUseCase(imageRepository: ImageRepository): UploadImageUseCase {
        return UploadImageUseCase(imageRepository)
    }

    @Provides
    @Singleton
    fun provideSubmitBugUseCase(bugFormRepository: BugFormRepository): SubmitBugUseCase {
        return SubmitBugUseCase(bugFormRepository)
    }

    @Provides
    @Singleton
    fun provideBugListUseCase(homeRepository: HomeRepository): BugListUseCase {
        return BugListUseCase(homeRepository)
    }
}