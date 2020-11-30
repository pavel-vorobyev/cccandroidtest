package com.pavelvorobyev.cccandroidtest.businesslogic.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Person
import io.reactivex.Maybe

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Person): Maybe<Long>

}