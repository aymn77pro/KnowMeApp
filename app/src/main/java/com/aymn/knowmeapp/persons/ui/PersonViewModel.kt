package com.aymn.knowmeapp.persons.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aymn.knowmeapp.network.model.PersonInformation
import com.aymn.knowmeapp.persons.domain.SetPersonDataUseCase
import kotlinx.coroutines.launch

class PersonViewModel(private val setPersonDataUseCase: SetPersonDataUseCase):ViewModel() {



    private fun setPersonData(personInformation: PersonInformation){
        viewModelScope.launch {
            setPersonDataUseCase.invoke(personInformation)
        }
    }
//    fun savePersonData(name:String,phone:String,email:String,)
}