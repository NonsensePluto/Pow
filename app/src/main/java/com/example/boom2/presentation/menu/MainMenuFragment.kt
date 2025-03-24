package com.example.boom2.presentation.menu

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.boom2.R
import com.example.boom2.data.Navigator
import com.example.boom2.databinding.ActivityMainMenuBinding
import com.example.boom2.presentation.settings.SettingsFragment
import com.example.boom2.presentation.confirms.ConfirmSettingsFragment

class MainMenuFragment : Fragment(R.layout.activity_main_menu) {

    private var binding : ActivityMainMenuBinding? = null
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Ничего не делаем - просто игнорируем
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ActivityMainMenuBinding.bind(view)

        binding?.startButton?.setOnClickListener {
            val dialog = ConfirmSettingsFragment().newInstance()
            dialog.show(parentFragmentManager, "ConfirmSettingsDialog")
        }

        binding?.settingButton?.setOnClickListener { Navigator.navigate(parentFragmentManager, SettingsFragment())/*navigate(SettingsFragment())*/ }

        binding?.ruleButton?.setOnClickListener { Navigator.navigate(parentFragmentManager, RuleFragment()) }


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}