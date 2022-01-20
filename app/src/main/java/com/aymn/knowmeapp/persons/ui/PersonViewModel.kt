package com.aymn.knowmeapp.persons.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aymn.knowmeapp.network.model.PersonInformation
import com.aymn.knowmeapp.persons.domain.*
import com.aymn.knowmeapp.userInfo.domain.SearchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonViewModel(
    private val setPersonDataUseCase: SetPersonDataUseCase,
    private val getPersonDataUseCase: GetPersonDataUseCase,
    private val getOnePersonUseCase: GetOnePersonUseCase,
    private val setOnePersoneData: SetOnePersoneData,
    private val deletePersonInformationUseCase: DeletePersonInformationUseCase,
    private val getImportedListUseCase: GetImportedListUseCase,
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _persons = MutableStateFlow<List<PersonInformation>>(emptyList())
    val persons = _persons.asLiveData()

    private val _personData = MutableStateFlow(PersonInformation())
    val personData = _personData.asLiveData()

    private val _personsImported = MutableStateFlow<List<PersonInformation>>(emptyList())
    val personsImported = _personsImported.asLiveData()

    fun setPersonData(personInformation: PersonInformation, uri: Uri?) {
        viewModelScope.launch {
            setPersonDataUseCase.invoke(personInformation, uri)
        }
    }

    fun getPersonData() {
        viewModelScope.launch {
            getPersonDataUseCase.invoke().collect { persons ->
                _persons.update { persons }
                Log.e("TAG", "getPersonData: $persons")

            }
        }
    }

//    fun getPersonDataSearch(query : String) {
//        viewModelScope.launch {
//            getPersonDataUseCase.invoke().collect { persons ->
//                _persons.update { persons }
//            }
//        }
//        _persons.find
//    }

    fun getOnePerson(id: String) {
        viewModelScope.launch {
            getOnePersonUseCase.invoke(id).collect { onePersonData ->
                _personData.update { onePersonData }
            }
        }
    }

    fun setOnePerson(id: String, personInformation: PersonInformation, uri: Uri?) {
        viewModelScope.launch {
            setOnePersoneData.invoke(id, personInformation, uri)
        }
    }

    fun deletePerson(id: String) {
        viewModelScope.launch {
            deletePersonInformationUseCase.invoke(id)
        }
    }

    fun getImportedList() {
        viewModelScope.launch {
            getImportedListUseCase.invoke().collect { importedList ->
                _personsImported.update { importedList }

            }
        }
    }

    fun search(newText: String) {
        viewModelScope.launch {
            searchUseCase.invoke(newText).collect { list ->
                _persons.update { list }
            }
        }
    }
}




