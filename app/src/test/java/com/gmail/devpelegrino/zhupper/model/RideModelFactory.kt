package com.gmail.devpelegrino.zhupper.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object RideModelFactory {

    fun createFull(): RideModel {
        return RideModel(
            id = RandomDataGenerator.randomInt(),
            date = RandomDataGenerator.randomString(),
            origin = RandomDataGenerator.randomString(),
            destination = RandomDataGenerator.randomString(),
            distance = RandomDataGenerator.randomDouble(),
            duration = RandomDataGenerator.randomString(),
            driver = DriverModelFactory.createFull(),
            value = RandomDataGenerator.randomDouble()
        )
    }
}
