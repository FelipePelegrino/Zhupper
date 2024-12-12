package com.gmail.devpelegrino.zhupper.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object DriverModelFactory {

    fun createFull(): DriverModel {
        return DriverModel(
            id = RandomDataGenerator.randomInt(),
            name = RandomDataGenerator.randomString()
        )
    }
}
