package com.example.boom2.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.boom2.R
import com.example.boom2.data.Navigator
import com.example.boom2.databinding.ActivityMainMenuBinding

class MainMenuFragment : Fragment(R.layout.activity_main_menu) {

    private var binding : ActivityMainMenuBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ActivityMainMenuBinding.bind(view)

        binding?.startButton?.setOnClickListener {
            val dialog = ConfirmSettingsFragment().newInstance()
            dialog.show(parentFragmentManager, "ConfirmSettingsDialog")
        }

        binding?.settingButton?.setOnClickListener { Navigator.navigate(parentFragmentManager, SettingsFragment())/*navigate(SettingsFragment())*/ }

        binding?.ruleButton?.setOnClickListener { Navigator.navigate(parentFragmentManager, RuleFragment()) }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}