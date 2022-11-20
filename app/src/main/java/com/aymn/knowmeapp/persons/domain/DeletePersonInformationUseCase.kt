package com.aymn.knowmeapp.persons.domain

import com.aymn.knowmeapp.persons.data.PersonRepository

class DeletePersonInformationUseCase(private val personRepository: PersonRepository) {

    suspend operator fun invoke(id:String) = personRepository.deletePersoneData(id)
}