package com.example.videominiplayer.p6f5d0c23.platform

import android.app.Application
import com.pico.spatial.ui.foundation.dsl.launch
import com.example.videominiplayer.p6f5d0c23.mainApp

class SpatialApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        launch(::mainApp)
    }
}
