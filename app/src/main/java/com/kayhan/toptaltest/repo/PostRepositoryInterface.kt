package com.kayhan.toptaltest.repo

import androidx.lifecycle.LiveData
import com.kayhan.toptaltest.models.ImageResponse
import com.kayhan.toptaltest.roomdb.Post
import com.kayhan.toptaltest.util.Resource

interface PostRepositoryInterface {

    suspend fun insertPost(post:Post)

    suspend fun deletePost(post:Post)

    fun getPosts(): LiveData<List<Post>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>
}