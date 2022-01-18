package com.aymn.knowmeapp.persons.data

import android.net.Uri
import com.aymn.knowmeapp.network.model.PersonInformation

class PersonRepository(private val personeDataSource: PersoneDataSource) {

    suspend fun setPersonInformation(personInformation: PersonInformation,uri: Uri?)=personeDataSource.setPersonInformation(personInformation,uri)

    suspend fun getPersonData() = personeDataSource.getPersonsData()

    suspend fun getOnePersonData(id:String) = personeDataSource.getOnePersonData(id)

    suspend fun setOnePersoneData(id: String,personInformation: PersonInformation,uri: Uri?) = personeDataSource.setOnePersonData(id,personInformation,uri)

    suspend fun deletePersoneData(id:String) = personeDataSource.deletePersonData(id)

    suspend fun getImportedList() = personeDataSource.getImportedList()
}