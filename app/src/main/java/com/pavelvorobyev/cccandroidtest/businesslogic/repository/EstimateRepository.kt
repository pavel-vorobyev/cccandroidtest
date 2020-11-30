package com.pavelvorobyev.cccandroidtest.businesslogic.repository

import com.pavelvorobyev.cccandroidtest.businesslogic.db.dao.EstimateDao
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Estimate
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.EstimateAndPerson
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class EstimateRepository(private val estimateDao: EstimateDao) {

    fun addEstimate(estimate: Estimate): Maybe<Long> {
        return estimateDao.insert(estimate)
            .subscribeOn(Schedulers.io())
    }

    fun getEstimate(id: String): Flowable<EstimateAndPerson> {
        return estimateDao.getEstimate(id)
            .subscribeOn(Schedulers.io())
    }

}