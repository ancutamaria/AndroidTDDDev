package com.am.groovy.playlist

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {

    @Provides
    fun playlistAPI(retrofit: Retrofit) =
        retrofit.create(PlaylistAPI::class.java)

    @Provides
    fun retrofit() = Retrofit.Builder()
            .baseUrl("http://192.168.1.8:2999/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}