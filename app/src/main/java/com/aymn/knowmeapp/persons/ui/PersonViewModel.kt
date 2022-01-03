package com.aymn.knowmeapp.persons.ui

import androidx.lifecycle.*
import com.aymn.knowmeapp.network.model.PersonInformation
import com.aymn.knowmeapp.network.model.personDataItem
import com.aymn.knowmeapp.persons.domain.GetOnePersonUseCase
import com.aymn.knowmeapp.persons.domain.GetPersonDataUseCase
import com.aymn.knowmeapp.persons.domain.SetOnePersoneData
import com.aymn.knowmeapp.persons.domain.SetPersonDataUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PersonViewModel(private val setPersonDataUseCase: SetPersonDataUseCase,private val getPersonDataUseCase: GetPersonDataUseCase
,private val getOnePersonUseCase: GetOnePersonUseCase,private val setOnePersoneData: SetOnePersoneData
):ViewModel() {

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

    fun setOnePerson(id:String,personInformation: PersonInformation){
        viewModelScope.launch {
             setOnePersoneData.invoke(id,personInformation)
        }
    }


}




