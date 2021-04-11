package com.am.groovy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.am.groovy.playlist.PlaylistFragment
import dagger.hilt.android.AndroidEntryPoint

// this annotation enables member injection in our android components,
// it can be used for: Activities, Fragments, Services and Broadcast Receivers
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, PlaylistFragment.newInstance())
                .commit()
        }
    }
}