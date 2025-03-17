package com.example.boom2.data

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var words: MutableList<String> = mutableListOf()
    private val fileName = DEFAULT_FILE_NAME

    init {
        loadWords()
    }

    private fun loadWords() {
        try {
            context.assets.open(fileName)
                .bufferedReader()
                .useLines { lines ->
                    words = lines.toMutableList()
                }
        } catch (e: IOException) {
            Log.e("WordManager", "Ошибка при чтении файла", e)
        }

    }

    fun selectRandomWords(countOfWords: Int): MutableList<String> {
        return words.shuffled().take(countOfWords).toMutableList()
    }

    fun selectRandomWord(unGuessedWords: MutableList<String>):String? {
        if (unGuessedWords.isEmpty()) return null
        return unGuessedWords.random()
    }


    companion object {
        private const val DEFAULT_FILE_NAME = "words"
    }
}