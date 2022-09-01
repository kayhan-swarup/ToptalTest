package com.kayhan.toptaltest.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.kayhan.toptaltest.adapter.ImageRecyclerAdapter
import com.kayhan.toptaltest.adapter.PostRecyclerAdapter
import javax.inject.Inject

class PostFragmentFactory @Inject constructor(
    private val postRecyclerAdapter: PostRecyclerAdapter,
    private val glide: RequestManager,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            PostFragment::class.java.name->PostFragment(postRecyclerAdapter)
            PostDetailsFragment::class.java.name -> PostDetailsFragment(glide)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }


    }
}