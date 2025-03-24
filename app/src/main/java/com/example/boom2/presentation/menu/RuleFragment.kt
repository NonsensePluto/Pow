package com.example.boom2.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.boom2.R
import com.example.boom2.databinding.ActivityRulesBinding
import com.example.boom2.databinding.ActivitySettingsBinding
import com.example.boom2.presentation.settings.SettingsViewModel
import java.io.File

class RuleFragment: Fragment(R.layout.activity_rules) {

    private lateinit var binding: ActivityRulesBinding

    private val fileName: String = "rules"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityRulesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            // Чтение файла из assets
            val text = readTextFromAssets(fileName)
            binding.textView.text = text
        } catch (e: Exception) {
            binding.textView.text = "Ошибка при чтении файла: ${e.localizedMessage}"
        }
    }

    private fun readTextFromAssets(fileName: String): String {
        return requireContext().assets.open(fileName).bufferedReader().use { it.readText() }
    }

}