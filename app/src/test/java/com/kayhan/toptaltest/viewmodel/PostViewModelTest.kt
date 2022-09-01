package com.kayhan.toptaltest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kayhan.toptaltest.MainCoroutineRule
import com.kayhan.toptaltest.repo.FakePostRepository
import com.kayhan.toptaltest.roomdb.Post
import com.kayhan.toptaltest.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostViewModelTest {

    private lateinit var viewModel:PostViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun init(){
        viewModel = PostViewModel(FakePostRepository())
    }

    @Test
    fun `insert post without title test`(){
        viewModel.makePost("","Kayhan","Dhaka")
        val value = viewModel.insertPostMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }


}