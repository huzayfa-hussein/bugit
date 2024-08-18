package com.hu.bugit.data.di


import com.hu.bugit.data.common.Constants.IMAGE_BASE_URL
import com.hu.bugit.data.common.Constants.NOTION_BASE_URL
import com.hu.bugit.data.persistence.BugDao
import com.hu.bugit.data.remote.interceptor.ServiceHeadersInterceptor
import com.hu.bugit.data.remote.ImageApiService
import com.hu.bugit.data.remote.NotionApiService
import com.hu.bugit.data.remote.handler.ApiHandler
import com.hu.bugit.data.repository.BugFormRepositoryImpl
import com.hu.bugit.data.repository.HomeRepositoryImpl
import com.hu.bugit.data.repository.ImageRepositoryImpl
import com.hu.bugit.domain.repository.bugForm.BugFormRepository
import com.hu.bugit.domain.repository.bugForm.ImageRepository
import com.hu.bugit.domain.repository.home.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides an instance of HttpLoggingInterceptor with logging level set to BODY.
     * @return HttpLoggingInterceptor instance.
     */
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                HttpLoggingInterceptor.Level.BODY
        }
    }


    @Provides
    @Singleton
    fun provideServiceHeaderInterceptor(): ServiceHeadersInterceptor {
        return ServiceHeadersInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        serviceHeadersInterceptor: ServiceHeadersInterceptor
    ): OkHttpClient {
        // Build OkHttpClient instance with custom configurations
        return OkHttpClient.Builder().apply {
            // Set connection timeout to 60 seconds
            this.connectTimeout(60, TimeUnit.SECONDS)
            // Set read timeout to 60 seconds
            this.readTimeout(60, TimeUnit.SECONDS)
            // Add HTTP logging interceptor for logging requests and responses
            this.addInterceptor(serviceHeadersInterceptor)
            this.addInterceptor(httpLoggingInterceptor)
        }.build() // Build the OkHttpClient instance
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NOTION_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNotionApiService(retrofit: Retrofit): NotionApiService {
        return retrofit.create(NotionApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("ImageRetrofit")
    fun provideImageRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(IMAGE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideImageApiService(@Named("ImageRetrofit") imageRetrofit: Retrofit): ImageApiService {
        return imageRetrofit.create(ImageApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiHandler(): ApiHandler {
        return ApiHandler()
    }

    @Provides
    @Singleton
    fun provideImageRepository(
        imageApiService: ImageApiService,
        apiHandler: ApiHandler
    ): ImageRepository {
        return ImageRepositoryImpl(imageApiService, apiHandler)
    }

    @Provides
    @Singleton
    fun provideBugFormRepository(
        notionApiService: NotionApiService,
        apiHandler: ApiHandler,
        bugDao: BugDao
    ): BugFormRepository {
        return BugFormRepositoryImpl(notionApiService, apiHandler, bugDao)
    }


}