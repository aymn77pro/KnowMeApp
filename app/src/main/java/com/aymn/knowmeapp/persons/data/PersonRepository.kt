package com.aymn.knowmeapp.persons.data

import com.aymn.knowmeapp.network.model.PersonInformation

class PersonRepository(private val personeDataSource: PersoneDataSource) {

    suspend fun setPersonInformation(personInformation: PersonInformation)=personeDataSource.setPersonInformation(personInformation)

    suspend fun getPersonData() = personeDataSource.getPersonsData()

    suspend fun getOnePersonData(id:String) = personeDataSource.getOnePersonData(id)

    suspend fun setOnePersoneData(id: String,personInformation: PersonInformation) = personeDataSource.setOnePersonData(id,personInformation)


}