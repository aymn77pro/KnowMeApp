package com.aymn.knowmeapp.persons.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aymn.knowmeapp.network.model.PersonInformation
import com.aymn.knowmeapp.persons.domain.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonViewModel(
    private val setPersonDataUseCase: SetPersonDataUseCase,
    private val getPersonDataUseCase: GetPersonDataUseCase,
    private val getOnePersonUseCase: GetOnePersonUseCase,
    private val setOnePersoneData: SetOnePersoneData,
    private val deletePersonInformationUseCase: DeletePersonInformationUseCase
) : ViewModel() {

    private val _persons = MutableStateFlow<List<PersonInformation>>(emptyList())
    val persons = _persons.asLiveData()

    private val _personData = MutableStateFlow(PersonInformation())
    val personData = _personData.asLiveData()

    fun setPersonData(personInformation: PersonInformation) {
        viewModelScope.launch {
            setPersonDataUseCase.invoke(personInformation)
        }
    }

    fun getPersonData() {
        viewModelScope.launch {
            getPersonDataUseCase.invoke().collect { persons ->
                _persons.update { persons }
            }
        }
    }

    fun getOnePerson(id: String) {
        viewModelScope.launch {
            getOnePersonUseCase.invoke(id).collect { onePersonData ->
                _personData.update { onePersonData }
            }
        }
    }

    fun setOnePerson(id: String, personInformation: PersonInformation) {
        viewModelScope.launch {
            setOnePersoneData.invoke(id, personInformation)
        }
    }

    fun deletePerson(id: String){
        viewModelScope.launch {
            deletePersonInformationUseCase.invoke(id)
        }
    }
}




