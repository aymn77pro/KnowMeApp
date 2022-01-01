package com.aymn.knowmeapp.persons.data

import android.util.Log
import com.aymn.knowmeapp.network.model.PersonInformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await

class PersoneDataSource(
    private val firebaseDB : FirebaseFirestore
):PersonData {
    val db = firebaseDB
    val auth = Firebase.auth
//-----------------------------------start setPersonInformation-------------------------------------//
    override suspend fun setPersonInformation(personInformation: PersonInformation) {
    val document = db.collection("users").document("${auth.currentUser?.email}").collection("${auth.currentUser?.email}+persons")
    document.document(personInformation.Name).set(personInformation).await()
    }

    override suspend fun getPersonsData(): Flow<PersonInformation> = callbackFlow {
        val document = db.collection("users").document("${auth.currentUser?.email}").collection("${auth.currentUser?.email}+persons")
       // val get = document
        document.get().await()
            .documents.forEach{
            val parsonList = it.toObject(PersonInformation::class.java)
                       if (parsonList != null && it.exists()){
                        trySend(parsonList!!)
                   }
        }
        awaitClose {

        }
    }
//---------------------------------finish setPersonInformation--------------------------------------//
}