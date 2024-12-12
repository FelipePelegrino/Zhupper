package com.gmail.devpelegrino.zhupper.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object LocationModelFactory {

    fun createFull(): LocationModel {
        return LocationModel(
            latitude = RandomDataGenerator.randomDouble(),
            longitude = RandomDataGenerator.randomDouble()
        )
    }
}
