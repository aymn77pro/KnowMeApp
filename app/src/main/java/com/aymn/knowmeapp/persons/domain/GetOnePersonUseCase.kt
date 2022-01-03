package com.aymn.knowmeapp.persons.domain

import com.aymn.knowmeapp.network.model.PersonInformation
import com.aymn.knowmeapp.persons.data.PersonRepository

class GetOnePersonUseCase (private val personRepository: PersonRepository) {

    suspend operator fun invoke(id: String) = personRepository.getOnePersonData(id)
}
