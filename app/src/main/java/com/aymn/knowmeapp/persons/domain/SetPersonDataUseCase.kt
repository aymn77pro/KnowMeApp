package com.aymn.knowmeapp.persons.domain

import android.net.Uri
import com.aymn.knowmeapp.network.model.PersonInformation
import com.aymn.knowmeapp.persons.data.PersonRepository

class SetPersonDataUseCase(private val personRepository: PersonRepository) {

    suspend operator fun invoke(personInformation: PersonInformation,uri: Uri?) = personRepository.setPersonInformation(personInformation,uri)

}