package com.pavelvorobyev.cccandroidtest.businesslogic.repository

import com.pavelvorobyev.cccandroidtest.businesslogic.db.dao.PersonDao
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PersonRepository(private val personDao: PersonDao) {

    @ExperimentalCoroutinesApi
    suspend fun addPerson(person: Person): Flow<Long> {
        return flow {
            val result = personDao.insert(person)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}