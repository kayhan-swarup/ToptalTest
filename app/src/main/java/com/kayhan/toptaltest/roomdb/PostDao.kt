package com.kayhan.toptaltest.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post:Post)

    @Delete
    suspend fun deletePost(post:Post)

    @Query("SELECT * FROM posts")
    fun observePosts(): LiveData<List<Post>>

}