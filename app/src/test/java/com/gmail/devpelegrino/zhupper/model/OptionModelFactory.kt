package com.gmail.devpelegrino.zhupper.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object OptionModelFactory {

    fun createFull(): OptionModel {
        return OptionModel(
            id = RandomDataGenerator.randomInt(),
            name = RandomDataGenerator.randomString(10),
            description = RandomDataGenerator.randomString(20),
            vehicle = RandomDataGenerator.randomString(10),
            review = ReviewModelFactory.createFull(),
            value = RandomDataGenerator.randomDouble()
        )
    }
}