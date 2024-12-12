package com.gmail.devpelegrino.zhupper.network.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object NetworkDriverFactory {

    fun createFull(): NetworkDriver {
        return NetworkDriver(
            id = RandomDataGenerator.randomInt(),
            name = RandomDataGenerator.randomString()
        )
    }
}
