package com.glushko.sportcommunity

import android.app.Application
import coil.Coil
import coil.ImageLoader
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        val imageLoader = ImageLoader.Builder(this)
            .placeholder(R.drawable.ic_healing_black_36dp)
            .error(R.drawable.ic_healing_black_36dp)
            .build()
        Coil.setImageLoader(imageLoader)
    }
}