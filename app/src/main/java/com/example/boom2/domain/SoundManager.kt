package com.example.boom2.domain

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.SparseIntArray
import com.example.boom2.R

class SoundManager(context: Context) {
    private val soundPool: SoundPool
    private val soundMap = SparseIntArray()
    private var loaded = false

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(6) // Максимум одновременных звуков
            .setAudioAttributes(audioAttributes)
            .build()

        soundPool.setOnLoadCompleteListener { _, _, status ->
            loaded = status == 0
        }

        // Загрузка звуков
        soundMap.put(R.raw.guessed, soundPool.load(context, R.raw.guessed, 1))
        soundMap.put(R.raw.shoot, soundPool.load(context, R.raw.shoot, 1))
        soundMap.put(R.raw.backtrack, soundPool.load(context, R.raw.backtrack, 1))
    }

    fun playGuessedSound() {
        playSound(R.raw.guessed)
    }

    fun playEndTimeSound() {
        playSound(R.raw.shoot)
    }

    fun playBacktrackSound() {
        playSound(R.raw.backtrack)
    }

    private fun playSound(resId: Int) {
        if (!loaded) return
        val soundId = soundMap.get(resId, -1)
        if (soundId != -1) {
            soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    fun release() {
        soundPool.release()
    }
}