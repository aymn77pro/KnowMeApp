package com.aymn.knowmeapp.persons.domain

import com.aymn.knowmeapp.network.model.PersonInformation
import com.aymn.knowmeapp.persons.data.PersonRepository

class SetOnePersoneData(private val personRepository: PersonRepository) {

    suspend operator fun invoke(id:String,personInformation: PersonInformation) = personRepository.setOnePersoneData(id,personInformation)
}