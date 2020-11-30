package com.pavelvorobyev.cccandroidtest.di

import android.content.Context
import com.pavelvorobyev.cccandroidtest.businesslogic.db.Db
import com.pavelvorobyev.cccandroidtest.businesslogic.db.dao.EstimateDao
import com.pavelvorobyev.cccandroidtest.businesslogic.db.dao.PersonDao
import com.pavelvorobyev.cccandroidtest.businesslogic.repository.EstimateRepository
import com.pavelvorobyev.cccandroidtest.businesslogic.repository.PersonRepository
import com.pavelvorobyev.cccandroidtest.view.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dbModule = module {
    fun getDb(context: Context): Db {
        return Db.getDb(context)
    }

    fun providePersonDao(db: Db): PersonDao {
        return db.personDao()
    }

    fun provideEstimateDao(db: Db): EstimateDao {
        return db.estimateDao()
    }

    single { getDb(androidContext()) }
    single { providePersonDao(get()) }
    single { provideEstimateDao(get()) }
}

val repositoryModule = module {
    single { PersonRepository(get()) }
    single { EstimateRepository(get()) }
}

val viewModelModule = module {
    viewModel {
        MainViewModel(androidApplication(), get(), get())
    }
}