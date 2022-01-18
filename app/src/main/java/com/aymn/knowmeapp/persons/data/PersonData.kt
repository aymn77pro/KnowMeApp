package com.aymn.knowmeapp.persons.data

import android.net.Uri
import com.aymn.knowmeapp.network.model.PersonInformation
import kotlinx.coroutines.flow.Flow

interface PersonData {
    //add new person to the  user list
    suspend fun setPersonInformation(personInformation:PersonInformation,uri: Uri?)
    //show the user has list
    suspend fun getPersonsData(): Flow<List<PersonInformation>>
    //show the user the person details
    suspend fun getOnePersonData(id:String):Flow<PersonInformation>
    //update person details
    suspend fun setOnePersonData(id:String,personInformation: PersonInformation,uri: Uri?)
    // delete person data from fire store
    suspend fun deletePersonData(id: String)
    //filter list to show only imported persons
    suspend fun getImportedList():Flow<List<PersonInformation>>


}