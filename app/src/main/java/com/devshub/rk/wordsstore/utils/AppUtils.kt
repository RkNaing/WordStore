package com.devshub.rk.wordsstore.utils

import androidx.annotation.IntRange
import java.util.*

/**
 * Created by ZMN on 2019-11-13.
 **/
internal fun getNumberUID(@IntRange(from = 1,to = 999) length: Int): String {
    val random = Random()
    val generatedRandomNumbers: List<Int> = List(length) {
        random.nextInt(10)
    }.shuffled(random)
    return generatedRandomNumbers.joinToString(separator = "")
}

internal fun getRandomNumber(@IntRange(from = 1,to = 999) length: Int): Int = getNumberUID(length).toInt()