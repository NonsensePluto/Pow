package com.example.boom2.presentation.confirms


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.boom2.R
import com.example.boom2.data.Navigator
import com.example.boom2.presentation.game.GameLobbyFragment
import com.example.boom2.presentation.settings.SettingsViewModel

class ConfirmSettingsFragment:DialogFragment() {

    private val viewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.confirm_settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val teamText = view.findViewById<TextView>(R.id.teamsCountTextView)
        val wordsCountText = view.findViewById<TextView>(R.id.wordsCountTextView)
        val roundTimerText = view.findViewById<TextView>(R.id.roundTimeTextView)
        val forthRoundText = view.findViewById<TextView>(R.id.forthRoundTextView)
        val hardcoreText = view.findViewById<TextView>(R.id.hardcoreTextView)

        val confirmButton = view.findViewById<Button>(R.id.confirmSettingsButton)



        viewModel.countOfWords.observe(viewLifecycleOwner) { countOfWords ->
            wordsCountText.text = "Количество слов: $countOfWords"
        }

        viewModel.countOfTeams.observe(viewLifecycleOwner) { countOfTeams ->
            teamText.text = "Количество команд: $countOfTeams"
        }

        viewModel.roundTime.observe(viewLifecycleOwner) { roundTime ->
            roundTimerText.text = "Время на раунд: ${roundTime} сек."
        }

        viewModel.forthRound.observe(viewLifecycleOwner) { forthRound ->
            if(forthRound == true) {
                forthRoundText.text = "Раунд \"Одна буква\": Вкл"
            } else {
                forthRoundText.text = "Раунд \"Одна буква\": Выкл"
            }
        }

        viewModel.hardCoreMode.observe(viewLifecycleOwner) { hardcoreFlag ->
            if(hardcoreFlag == true) {
                hardcoreText.text = "Режим Хардкор: Вкл"
            } else {
                hardcoreText.text = "Режим Хардкор: Выкл"
            }
        }

        confirmButton.setOnClickListener {
            Navigator.navigate(parentFragmentManager, GameLobbyFragment())
            dismiss()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // Прозрачный фон
        dialog.window?.setDimAmount(0.5f) // Уровень затемнения
        return dialog
    }


    fun newInstance(): ConfirmSettingsFragment {
        return ConfirmSettingsFragment()
    }

}