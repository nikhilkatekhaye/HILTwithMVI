package com.org.hilt_android_demo.repository

import com.org.hilt_android_demo.model.Blog
import com.org.hilt_android_demo.retrofit.BlogRetrofit
import com.org.hilt_android_demo.retrofit.NetworkMapper
import com.org.hilt_android_demo.room.BlogDao
import com.org.hilt_android_demo.room.CacheMapper
import com.org.hilt_android_demo.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlog(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlog = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlog)
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))

        } catch (e: Exception) {

            emit(DataState.Error(e))
        }
    }
}