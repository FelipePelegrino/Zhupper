package com.gmail.devpelegrino.zhupper.network.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object NetworkLocationFactory {

    fun createFull(): NetworkLocation {
        return NetworkLocation(
            latitude = RandomDataGenerator.randomDouble(),
            longitude = RandomDataGenerator.randomDouble()
        )
    }
}
