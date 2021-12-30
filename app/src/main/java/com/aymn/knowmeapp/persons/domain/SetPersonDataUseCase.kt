package com.aymn.knowmeapp.persons.domain

import com.aymn.knowmeapp.network.model.PersonInformation
import com.aymn.knowmeapp.persons.data.PersonRepository

class SetPersonDataUseCase(private val personRepository: PersonRepository) {

    suspend operator fun invoke(personInformation: PersonInformation) = personRepository.setPersonInformation(personInformation)

}