package com.org.hilt_android_demo.di

import com.org.hilt_android_demo.repository.MainRepository
import com.org.hilt_android_demo.retrofit.BlogRetrofit
import com.org.hilt_android_demo.retrofit.NetworkMapper
import com.org.hilt_android_demo.room.BlogDao
import com.org.hilt_android_demo.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        blogRetrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {

        return MainRepository(blogDao, blogRetrofit, cacheMapper, networkMapper)
    }
}