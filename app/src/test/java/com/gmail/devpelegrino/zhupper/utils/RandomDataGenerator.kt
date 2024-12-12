package com.gmail.devpelegrino.zhupper.utils

import kotlin.random.Random

object RandomDataGenerator {

    fun randomString(length: Int = 100): String {
        val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length).map { chars.random() }.joinToString("")
    }

    fun randomInt(min: Int = 0, max: Int = 100): Int {
        return Random.nextInt(min, max + 1)
    }

    fun randomDouble(min: Double = 0.0, max: Double = 100.0): Double {
        return Random.nextDouble(min, max)
    }
}