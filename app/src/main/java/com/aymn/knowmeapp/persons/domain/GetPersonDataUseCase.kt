package com.aymn.knowmeapp.persons.domain

import com.aymn.knowmeapp.persons.data.PersonRepository

class GetPersonDataUseCase(private val personRepository: PersonRepository) {
    suspend operator fun invoke() = personRepository.getPersonData()
}