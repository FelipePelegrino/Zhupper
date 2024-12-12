package com.gmail.devpelegrino.zhupper.network.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object NetworkRideFactory {

    fun createFull(): NetworkRide {
        return NetworkRide(
            id = RandomDataGenerator.randomInt(),
            date = RandomDataGenerator.randomString(),
            destination = RandomDataGenerator.randomString(),
            origin = RandomDataGenerator.randomString(),
            duration = RandomDataGenerator.randomString(),
            distance = RandomDataGenerator.randomDouble(),
            driver = NetworkDriverFactory.createFull(),
            value = RandomDataGenerator.randomDouble()
        )
    }
}
