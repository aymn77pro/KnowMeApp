package com.aymn.knowmeapp.persons.domain

import com.aymn.knowmeapp.persons.data.PersonRepository

class GetImportedListUseCase(private val personRepository: PersonRepository) {
   suspend  operator fun invoke()= personRepository.getImportedList()
}