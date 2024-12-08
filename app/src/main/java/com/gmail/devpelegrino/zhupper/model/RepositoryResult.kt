package com.gmail.devpelegrino.zhupper.model

sealed class RepositoryResult<out T> {
    data class Success<T>(val data: T) : RepositoryResult<T>()

    data class ApiError(
        val errorCode: String,
        val errorDescription: String
    ) : RepositoryResult<Nothing>()

    data class UnexpectedError(val message: String) : RepositoryResult<Nothing>()
}
