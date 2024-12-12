package com.gmail.devpelegrino.zhupper.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object EstimateRideModelFactory {

    fun createFull(): EstimateRideModel {
        return EstimateRideModel(
            origin = LocationModelFactory.createFull(),
            destination = LocationModelFactory.createFull(),
            distance = RandomDataGenerator.randomDouble(),
            duration = RandomDataGenerator.randomString(10),
            options = List(RandomDataGenerator.randomInt(1, 5)) {
                OptionModelFactory.createFull()
            },
            routeResponse = RandomDataGenerator.randomString()
        )
    }

    fun createEmptyOptions(): EstimateRideModel {
        return EstimateRideModel(
            origin = LocationModelFactory.createFull(),
            destination = LocationModelFactory.createFull(),
            distance = RandomDataGenerator.randomDouble(),
            duration = RandomDataGenerator.randomString(10),
            options = emptyList(),
            routeResponse = RandomDataGenerator.randomString()
        )
    }
}