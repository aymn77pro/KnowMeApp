package com.aymn.knowmeapp.persons.data

import android.net.Uri
import com.aymn.knowmeapp.network.model.PersonInformation
import kotlinx.coroutines.flow.Flow

interface PersonData {

    suspend fun setPersonInformation(personInformation:PersonInformation,uri: Uri?)

    suspend fun getPersonsData(): Flow<List<PersonInformation>>

    suspend fun getOnePersonData(id:String):Flow<PersonInformation>

    suspend fun setOnePersonData(id:String,personInformation: PersonInformation,uri: Uri?)

    suspend fun deletePersonData(id: String)
}