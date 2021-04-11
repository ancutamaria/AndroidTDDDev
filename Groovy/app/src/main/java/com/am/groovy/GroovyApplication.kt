package com.am.groovy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// this annotation enables field, member and method injection
@HiltAndroidApp
class GroovyApplication: Application() {
}