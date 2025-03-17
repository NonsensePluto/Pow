package com.example.boom2.data

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.boom2.R

class Navigator {

    companion object {
        fun navigate(fragmentManager: FragmentManager, fragment: Fragment) {
            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}