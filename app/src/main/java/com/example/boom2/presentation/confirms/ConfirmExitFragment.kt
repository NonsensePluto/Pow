package com.example.boom2.presentation.confirms

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.boom2.R
import com.example.boom2.domain.Navigator
import com.example.boom2.presentation.game.GameViewModel
import com.example.boom2.presentation.menu.MainMenuFragment

class ConfirmExitFragment: DialogFragment() {

    private val viewModel: GameViewModel by activityViewModels()
    private lateinit var navigator: Navigator


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.confirm_exit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = Navigator()

        val confirmButton = view.findViewById<Button>(R.id.confirmExitButton)
        val rejectButton = view.findViewById<Button>(R.id.rejectExitButton)

        confirmButton.setOnClickListener {
            navigator.navigate(parentFragmentManager, MainMenuFragment())
            viewModel.resetGame()
            dismiss()
        }

        rejectButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // Прозрачный фон
        dialog.window?.setDimAmount(0.5f) // Уровень затемнения
        return dialog
    }

    fun newInstance(): ConfirmExitFragment {
        return ConfirmExitFragment()
    }
}