package com.gmail.devpelegrino.zhupper.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object ReviewModelFactory {

    fun createFull(): ReviewModel {
        return ReviewModel(
            rating = RandomDataGenerator.randomDouble(),
            comment = RandomDataGenerator.randomString()
        )
    }
}
