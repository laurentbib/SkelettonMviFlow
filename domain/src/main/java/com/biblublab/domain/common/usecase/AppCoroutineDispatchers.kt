package com.biblublab.domain.common.usecase

import kotlinx.coroutines.CoroutineDispatcher

data class AppCoroutineDispatchers(val io: CoroutineDispatcher,
                                   val computation: CoroutineDispatcher,
                                   val main: CoroutineDispatcher
)