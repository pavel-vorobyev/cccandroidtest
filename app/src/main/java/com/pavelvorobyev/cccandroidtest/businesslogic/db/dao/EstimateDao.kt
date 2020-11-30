package com.pavelvorobyev.cccandroidtest.businesslogic.db.dao

import androidx.room.*
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Estimate
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.EstimateAndPerson

@Dao
interface EstimateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(estimate: Estimate): Long

    @Transaction
    @Query("SELECT * FROM estimates WHERE id = :id")
    suspend fun getEstimate(id: String): EstimateAndPerson

}