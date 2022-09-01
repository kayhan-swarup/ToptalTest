package com.kayhan.toptaltest.repo

import androidx.lifecycle.LiveData
import com.kayhan.toptaltest.api.RetrofitApi
import com.kayhan.toptaltest.models.ImageResponse
import com.kayhan.toptaltest.roomdb.Post
import com.kayhan.toptaltest.roomdb.PostDao
import com.kayhan.toptaltest.util.Resource
import java.lang.Exception
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postDao: PostDao,
    private val retrofitApi: RetrofitApi,
    ) : PostRepositoryInterface {
    override suspend fun insertPost(post: Post) {
        postDao.insertPost(post)
    }

    override suspend fun deletePost(post: Post) {
        postDao.deletePost(post)
    }

    override fun getPosts(): LiveData<List<Post>> {
        return postDao.observePosts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try{
            val response = retrofitApi.imageSearch(imageString)
            if(response.isSuccessful){
                response.body()?.let{
                    return@let Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                return Resource.error("Error",null)
            }
        }catch (e: Exception){
            Resource.error("No Data", null)
        }
    }
}