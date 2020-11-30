package com.pavelvorobyev.cccandroidtest.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pavelvorobyev.cccandroidtest.App
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Estimate
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.EstimateAndPerson
import com.pavelvorobyev.cccandroidtest.businesslogic.db.entity.Person
import com.pavelvorobyev.cccandroidtest.businesslogic.repository.EstimateRepository
import com.pavelvorobyev.cccandroidtest.businesslogic.repository.PersonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(application: Application,
                    private val personRepository: PersonRepository,
                    private val estimateRepository: EstimateRepository) : AndroidViewModel(application) {

    val viewState = MutableLiveData<ViewState>()

    @FlowPreview
    @ExperimentalCoroutinesApi
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

        viewModelScope.launch {
            personRepository.addPerson(person)
                .flatMapMerge {
                    estimateRepository.addEstimate(estimate)
                }
                .catch { e ->
                    e.printStackTrace()
                    viewState.value = ViewState.Error
                }.collect {
                    viewState.value = ViewState.DataInitialized
                }
        }
    }

    @ExperimentalCoroutinesApi
    fun getEstimate() {
        viewModelScope.launch {
            estimateRepository.getEstimate("c574b0b4-bdef-4b92-a8f0-608a86ccf5ab")
                .catch { e->
                    e.printStackTrace()
                }
                .collect { result ->
                    viewState.value = ViewState.Data(result)
                }
        }
    }

    sealed class ViewState {
        object InsertingData : ViewState()
        object DataInitialized : ViewState()
        data class Data(val data: EstimateAndPerson) : ViewState()
        object Error : ViewState()
    }

}