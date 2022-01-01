package com.aymn.knowmeapp.persons.ui

import android.util.Log
import androidx.lifecycle.*
import com.aymn.knowmeapp.network.model.PersonInformation
import com.aymn.knowmeapp.persons.domain.GetPersonDataUseCase
import com.aymn.knowmeapp.persons.domain.SetPersonDataUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonViewModel(private val setPersonDataUseCase: SetPersonDataUseCase,private val getPersonDataUseCase: GetPersonDataUseCase):ViewModel() {


    private var _person = MutableStateFlow(MutableLiveData<MutableList<PersonInformation>>(
    mutableListOf()))
    val person:StateFlow<MutableLiveData<MutableList<PersonInformation>>> = _person.asStateFlow()


     fun setPersonData(personInformation: PersonInformation){
        viewModelScope.launch {
            setPersonDataUseCase.invoke(personInformation)
        }

    }

    fun getPersonInfo(){
        viewModelScope
            .launch {
            getPersonDataUseCase.invoke()
                .collect {
                    _person.value.value?.add(it)
                    Log.d("TAG", "it=: ${it}")
                    }



        }
        }
    fun deletePersonInfo(){
        viewModelScope.launch {
            getPersonDataUseCase.invoke()
                .collect {
                    _person.value.value?.remove(it)

                }
        }
    }
}


