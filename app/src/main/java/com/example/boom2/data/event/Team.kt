package com.example.boom2.data.event

data class Team(
    val imageId: Int,
    val name: String,
    var wordsCount: Int
) {
    fun incrementWordsCount() {
        wordsCount++
    }

    fun decrementWordsCount() {
        if(wordsCount > 0) {
            wordsCount--
        }
    }
}
