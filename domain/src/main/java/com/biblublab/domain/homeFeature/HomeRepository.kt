package com.biblublab.domain.homeFeature

import com.biblublab.domain.common.usecase.Either
import com.biblublab.domain.common.usecase.Failure
import com.biblublab.domain.homeFeature.entities.HomeNews
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun fetchNews() : Flow<Either<Failure, List<HomeNews>>>
}