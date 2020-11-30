package com.pavelvorobyev.cccandroidtest.businesslogic.db.dao

import androidx.room.*
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Estimate
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.EstimateAndPerson
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface EstimateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(estimate: Estimate): Maybe<Long>

    @Transaction
    @Query("SELECT * FROM estimates WHERE id = :id")
    fun getEstimate(id: String): Flowable<EstimateAndPerson>

}