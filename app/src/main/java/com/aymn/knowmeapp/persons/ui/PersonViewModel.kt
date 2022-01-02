package com.aymn.knowmeapp.persons.ui

import android.util.Log
import androidx.lifecycle.*
import com.aymn.knowmeapp.network.model.PersonInformation
import com.aymn.knowmeapp.persons.domain.AddPersonUseCase
import com.aymn.knowmeapp.persons.domain.GetPersonDataUseCase
import com.aymn.knowmeapp.persons.domain.SetPersonDataUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.function.UnaryOperator

class PersonViewModel(private val setPersonDataUseCase: SetPersonDataUseCase,private val getPersonDataUseCase: GetPersonDataUseCase
,private val addPersonUseCase: AddPersonUseCase
):ViewModel() {

    private var _person = MutableStateFlow<List<PersonInformation>>(emptyList())
    val person  = _person.asLiveData()

    init {
     //   getPersonData()
            }
    fun setPersonData(personInformation: PersonInformation) {
        viewModelScope.launch {
            setPersonDataUseCase.invoke(personInformation)
        }
    }
    fun getPersonData(){
        viewModelScope.launch {
            getPersonDataUseCase.invoke().collect{ persons ->
                _person.update { persons }
            }
        }
    }
}




