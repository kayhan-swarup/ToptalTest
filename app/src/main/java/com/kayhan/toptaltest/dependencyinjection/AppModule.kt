package com.kayhan.toptaltest.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kayhan.toptaltest.R
import com.kayhan.toptaltest.api.RetrofitApi
import com.kayhan.toptaltest.repo.PostRepository
import com.kayhan.toptaltest.repo.PostRepositoryInterface
import com.kayhan.toptaltest.roomdb.PostDao
import com.kayhan.toptaltest.roomdb.PostDatabase
import com.kayhan.toptaltest.util.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,PostDatabase::class.java,"PostDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database: PostDatabase) = database.postDao()

    @Singleton
    @Provides
    fun injectRetrofitApi(): RetrofitApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Util.BASE_URL)
            .build()
            .create(RetrofitApi::class.java)

    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao: PostDao, api: RetrofitApi)=PostRepository(dao,api) as PostRepositoryInterface


    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context)= Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)

        )


}