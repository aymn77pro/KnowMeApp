package com.aymn.knowmeapp.persons.data

import com.aymn.knowmeapp.network.model.PersonInformation

class PersonRepository(private val personeDataSource: PersoneDataSource) {

    suspend fun setPersonInformation(personInformation: PersonInformation)=personeDataSource.setPersonInformation(personInformation)
}