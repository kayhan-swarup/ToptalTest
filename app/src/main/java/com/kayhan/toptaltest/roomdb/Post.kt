package com.kayhan.toptaltest.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
class Post(
    var title: String,
    var author: String,
    var location: String,
    var imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int?= null

)