package com.biblublab.skelettonmviflow.common.error

import com.biblublab.domain.common.usecase.Failure
import com.biblublab.skelettonmviflow.R

enum class UiFailure(val failure: Failure, val resLabel : Int) {
    NO_DATA(Failure.NO_DATA, R.string.failure_no_data),
    NETWORK_ERROR(Failure.NETWORK_ERROR, R.string.failure_network_issue),
    UNKNOWN_ERROR(Failure.UNKNOWN_ERROR, R.string.failure_unknown_error);

    companion object{
        fun fromDomain(failure: Failure) = values().find { it.failure == failure } ?: UNKNOWN_ERROR
    }
}