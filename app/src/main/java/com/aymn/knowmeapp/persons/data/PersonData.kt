package com.aymn.knowmeapp.persons.data

import com.aymn.knowmeapp.network.model.PersonInformation

interface PersonData {

    suspend fun setPersonInformation(personInformation:PersonInformation)
}