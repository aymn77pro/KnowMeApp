package com.aymn.knowmeapp.persons.data

import com.aymn.knowmeapp.network.model.PersonInformation
import kotlinx.coroutines.flow.Flow

interface PersonData {

    suspend fun setPersonInformation(personInformation:PersonInformation)

    suspend fun getPersonsData(): Flow<List<PersonInformation>>

   // suspend fun addPersonData(name:String):Flow<PersonInformation>
}