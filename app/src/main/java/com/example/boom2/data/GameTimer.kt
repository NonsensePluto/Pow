package com.example.boom2.data

import android.os.CountDownTimer

class GameTimer(
    private val totalTime: Long,
    private val intervalTime: Long,
    private val myOnTick:(Long) -> Unit,
    private val myOnFinish: () -> Unit
) {
    private var countDownTimer: CountDownTimer? = null

    fun start() {
        countDownTimer = object : CountDownTimer(totalTime, intervalTime) {
            override fun onTick(millisUntilFinish: Long) {
                myOnTick(millisUntilFinish)
            }

            override fun onFinish() {
                myOnFinish()
            }
        }.start()
    }

    fun stop() {
        countDownTimer?.cancel()
    }
}