package com.hu.bugit.data.di

import android.content.Context
import androidx.room.Room
import com.hu.bugit.data.common.Constants.DATABASE_NAME
import com.hu.bugit.data.persistence.AppDatabase
import com.hu.bugit.data.persistence.BugDao
import com.hu.bugit.data.repository.HomeRepositoryImpl
import com.hu.bugit.domain.repository.home.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .setQueryCallback(
                { sqlQuery, bindArgs -> println("SQL Query: $sqlQuery SQL Args: $bindArgs") },
                Executors.newSingleThreadExecutor()
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideBugDao(appDatabase: AppDatabase): BugDao {
        return appDatabase.bugDao()
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        bugDao: BugDao
    ): HomeRepository {
        return HomeRepositoryImpl(bugDao)
    }
}