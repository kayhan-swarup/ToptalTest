package com.kayhan.toptaltest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kayhan.toptaltest.models.ImageResponse
import com.kayhan.toptaltest.repo.PostRepositoryInterface
import com.kayhan.toptaltest.roomdb.Post
import com.kayhan.toptaltest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repositary: PostRepositoryInterface,
) : ViewModel() {
    val postList = repositary.getPosts()

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl : LiveData<String>
        get() = selectedImage

    private var insertPostMsg = MutableLiveData<Resource<Post>>()
    val insertPostMessage : LiveData<Resource<Post>>
        get() = insertPostMsg

    fun resetInsertPostMsg(){
        insertPostMsg = MutableLiveData<Resource<Post>>()
    }

    fun deletePost(post:Post) = viewModelScope.launch{
        repositary.deletePost(post)
    }

    fun insertPost(post:Post) = viewModelScope.launch {
        repositary.insertPost(post)
    }

    fun makePost(title:String, author: String, location: String){
        if(title.isEmpty()||author.isEmpty()||location.isEmpty()){
            insertPostMsg.postValue(Resource.error("Enter valid title, author name or location",null))
            return
        }

        val post = Post(title,author,location,selectedImage.value ?: "")
        insertPost(post)
        setSelectedImage("")
        insertPostMsg.postValue(Resource.success(post))

    }
    fun setSelectedImage(url : String) {
        selectedImage.postValue(url)
    }
    fun searchForImage(searchString: String){
        if(searchString.isEmpty())
            return
        images.value = Resource.loading(null)
        viewModelScope.launch {

            val response = repositary.searchImage(searchString)
            images.value = response
        }
    }
}