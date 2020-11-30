package com.pavelvorobyev.cccandroidtest.businesslogic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pavelvorobyev.cccandroidtest.businesslogic.db.dao.EstimateDao
import com.pavelvorobyev.cccandroidtest.businesslogic.db.dao.PersonDao
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Estimate
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Person

@Database(entities = [Person::class, Estimate::class], version = 1, exportSchema = false)
abstract class Db : RoomDatabase() {

    abstract fun personDao(): PersonDao
    abstract fun estimateDao(): EstimateDao

    companion object {
        private const val DB_NAME = "db"

        @Volatile
        private var instance: Db? = null

        fun getDb(context: Context): Db {
            val tempInstance = instance

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    Db::class.java,
                    DB_NAME
                ).build()
                instance = newInstance
                return newInstance
            }
        }
    }

}