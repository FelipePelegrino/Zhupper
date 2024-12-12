package com.gmail.devpelegrino.zhupper.network.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object NetworkResponseEstimateRideFactory {

    fun createFull(): NetworkResponseEstimateRide {
        return NetworkResponseEstimateRide(
            origin = NetworkLocationFactory.createFull(),
            destination = NetworkLocationFactory.createFull(),
            distance = RandomDataGenerator.randomDouble(),
            duration = RandomDataGenerator.randomString(),
            options = List(RandomDataGenerator.randomInt(1, 5)) {
                NetworkOptionFactory.createFull()
            }
        )
    }
}
