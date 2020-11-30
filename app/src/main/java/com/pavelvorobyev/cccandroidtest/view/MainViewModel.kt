package com.pavelvorobyev.cccandroidtest.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pavelvorobyev.cccandroidtest.App
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Estimate
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.EstimateAndPerson
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Person
import com.pavelvorobyev.cccandroidtest.businesslogic.repository.EstimateRepository
import com.pavelvorobyev.cccandroidtest.businesslogic.repository.PersonRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject

class MainViewModel(application: Application,
                    private val personRepository: PersonRepository,
                    private val estimateRepository: EstimateRepository) : AndroidViewModel(application) {

    private val disposable = CompositeDisposable()

    val viewState = MutableLiveData<ViewState>()

    fun initDbData() {
        viewState.value = ViewState.InsertingData

        val inputStream = getApplication<App>().assets.open("data.json")
        val json = inputStream.bufferedReader().use {
            it.readText()
        }

        val gson = Gson()
        val jsonObj = JSONObject(json)
        val person = gson.fromJson(jsonObj.getJSONObject("person").toString(), Person::class.java)
        val estimate = gson.fromJson(jsonObj.getJSONObject("estimate").toString(), Estimate::class.java)

        disposable.add(personRepository.addPerson(person)
            .flatMap {
                estimateRepository.addEstimate(estimate)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewState.value = ViewState.DataInitialized
                },
                { e ->
                    e.printStackTrace()
                    viewState.value = ViewState.Error
                }
            ))
    }

    fun getEstimate() {
        disposable.add(estimateRepository.getEstimate("c574b0b4-bdef-4b92-a8f0-608a86ccf5ab")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    viewState.value = ViewState.Data(result)
                },
                { e ->
                    e.printStackTrace()
                }
            ))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    sealed class ViewState {
        object InsertingData : ViewState()
        object DataInitialized : ViewState()
        data class Data(val data: EstimateAndPerson) : ViewState()
        object Error : ViewState()
    }

}