package com.kayhan.toptaltest.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.kayhan.toptaltest.viewmodel.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class PostDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: PostDao

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: PostDatabase

    @Before
    fun init(){
        /*database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),PostDatabase::class.java
        ).allowMainThreadQueries().build()*/
        hiltRule.inject()
        dao = database.postDao()
    }

    @After
    fun closeDB(){
        database.close()
    }

    @Test
    fun testInsertPost() = runBlockingTest{
        val post = Post("Test", "Test Author", "Test Location",
            "https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg",
            1)
        dao.insertPost(post)

        val list = dao.observePosts().getOrAwaitValue()
//        assertThat(list).contains(post)
        assertThat("${list.get(list.size-1).id}_${list.get(0).title}").isEqualTo("${post.id}_${post.title}")


    }

    @Test
    fun testDeletePost() = runBlockingTest{
        val post = Post("Test", "Test Author", "Test Location",
            "https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg",
            1)
        dao.insertPost(post)
        dao.deletePost(post)

        val list = dao.observePosts().getOrAwaitValue()
        assertThat(list).isEmpty()
    }


}