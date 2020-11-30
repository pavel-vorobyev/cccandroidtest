package com.pavelvorobyev.cccandroidtest.businesslogic.repository

import com.pavelvorobyev.cccandroidtest.businesslogic.db.dao.PersonDao
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Person
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class PersonRepository(private val personDao: PersonDao) {

    fun addPerson(person: Person): Maybe<Long> {
        return personDao.insert(person)
            .subscribeOn(Schedulers.io())
    }

}