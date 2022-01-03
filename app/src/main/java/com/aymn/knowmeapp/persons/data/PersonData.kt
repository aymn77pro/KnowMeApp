package com.aymn.knowmeapp.persons.data

import com.aymn.knowmeapp.network.model.PersonInformation
import kotlinx.coroutines.flow.Flow

interface PersonData {

    suspend fun setPersonInformation(personInformation:PersonInformation)

    suspend fun getPersonsData(): Flow<List<PersonInformation>>

    suspend fun getOnePersonData(id:String):Flow<PersonInformation>

    suspend fun setOnePersonData(id:String,personInformation: PersonInformation)

    suspend fun deletePersonData(id: String)
}