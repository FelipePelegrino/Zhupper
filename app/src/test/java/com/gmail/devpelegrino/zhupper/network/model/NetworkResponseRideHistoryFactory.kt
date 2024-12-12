package com.gmail.devpelegrino.zhupper.network.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object NetworkResponseRideHistoryFactory {

    fun createFull(): NetworkResponseRideHistory {
        return NetworkResponseRideHistory(
            customerId = RandomDataGenerator.randomString(),
            rides = List(RandomDataGenerator.randomInt(1, 5)) {
                NetworkRideFactory.createFull()
            }
        )
    }
}
