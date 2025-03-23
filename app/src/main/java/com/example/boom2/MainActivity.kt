package com.example.boom2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.boom2.presentation.menu.MainMenuFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    MainMenuFragment()
                )
                .commit()
        }
    }

}