package com.kayhan.toptaltest.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kayhan.toptaltest.models.ImageResponse
import com.kayhan.toptaltest.roomdb.Post
import com.kayhan.toptaltest.util.Resource

class FakePostRepository: PostRepositoryInterface {

    private val posts = mutableListOf<Post>()
    private val postLiveData = MutableLiveData<List<Post>>(posts)

    override suspend fun insertPost(post: Post) {
        posts.add(post)
    }

    override suspend fun deletePost(post: Post) {
        posts.remove(post)
        refreshData()
    }

    override fun getPosts(): LiveData<List<Post>> {
        return postLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }
    private fun refreshData(){
        postLiveData.postValue(posts)
    }
}