package com.pavelvorobyev.cccandroidtest.businesslogic.repository

import com.pavelvorobyev.cccandroidtest.businesslogic.db.dao.EstimateDao
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Estimate
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.EstimateAndPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EstimateRepository(private val estimateDao: EstimateDao) {

    @ExperimentalCoroutinesApi
    suspend fun addEstimate(estimate: Estimate): Flow<Long> {
        return flow {
            val result = estimateDao.insert(estimate)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    suspend fun getEstimate(id: String): Flow<EstimateAndPerson> {
        return flow {
            val estimate = estimateDao.getEstimate(id)
            emit(estimate)
        }.flowOn(Dispatchers.IO)
    }

}