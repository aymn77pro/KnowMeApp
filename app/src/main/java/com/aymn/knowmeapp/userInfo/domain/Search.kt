package com.aymn.knowmeapp.userInfo.domain

import com.aymn.knowmeapp.network.model.PersonInformation
import com.aymn.knowmeapp.persons.data.PersonRepository
import kotlinx.coroutines.flow.Flow

class SearchUseCase(private val personRepository: PersonRepository) {
    suspend operator fun invoke(name:String): Flow<List<PersonInformation>> = personRepository.searchForPerson(name)
}