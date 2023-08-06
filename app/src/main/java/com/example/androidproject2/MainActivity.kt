package com.example.androidproject2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentOne())
                .commit()

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_two, FragmentTwo())
                .commit()
        }
    }
}