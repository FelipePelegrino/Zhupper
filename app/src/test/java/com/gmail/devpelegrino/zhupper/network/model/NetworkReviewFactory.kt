package com.gmail.devpelegrino.zhupper.network.model

import com.gmail.devpelegrino.zhupper.utils.RandomDataGenerator

object NetworkReviewFactory {

    fun createFull(): NetworkReview {
        return NetworkReview(
            rating = RandomDataGenerator.randomDouble(),
            comment = RandomDataGenerator.randomString()
        )
    }
}
