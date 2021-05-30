package com.biblublab.domain.homeFeature

import com.biblublab.domain.common.usecase.AppCoroutineDispatchers
import com.biblublab.domain.common.usecase.Either
import com.biblublab.domain.common.usecase.Failure
import com.biblublab.domain.common.usecase.FlowUseCase
import com.biblublab.domain.homeFeature.entities.HomeNews
import kotlinx.coroutines.flow.Flow

class HomeUseCase(private val homeRepository: HomeRepository,
                  private val appCoroutineDispatchers: AppCoroutineDispatchers) : FlowUseCase<Unit, Failure, List<HomeNews>>(appCoroutineDispatchers.io) {

    override fun run(params: Unit): Flow<Either<Failure, List<HomeNews>>> {
        return homeRepository.fetchNews()
    }
}