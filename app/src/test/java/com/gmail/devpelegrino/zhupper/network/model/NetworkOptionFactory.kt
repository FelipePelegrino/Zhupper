package com.gmail.devpelegrino.zhupper.network.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object NetworkOptionFactory {

    fun createFull(): NetworkOption {
        return NetworkOption(
            id = RandomDataGenerator.randomInt(),
            name = RandomDataGenerator.randomString(),
            description = RandomDataGenerator.randomString(),
            vehicle = RandomDataGenerator.randomString(),
            review = NetworkReviewFactory.createFull(),
            value = RandomDataGenerator.randomDouble()
        )
    }
}
