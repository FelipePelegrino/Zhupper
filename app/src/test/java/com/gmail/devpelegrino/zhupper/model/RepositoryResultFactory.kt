package com.gmail.devpelegrino.zhupper.model

object RepositoryResultFactory {

    fun <T> createSuccess(data: T): RepositoryResult.Success<T> {
        return RepositoryResult.Success(data)
    }

    fun createApiError(errorCode: String, errorDescription: String): RepositoryResult.ApiError {
        return RepositoryResult.ApiError(errorCode, errorDescription)
    }

    fun createNetworkError(): RepositoryResult.NetworkError {
        return RepositoryResult.NetworkError
    }

    fun createUnexpectedError(): RepositoryResult.UnexpectedError {
        return RepositoryResult.UnexpectedError
    }
}
