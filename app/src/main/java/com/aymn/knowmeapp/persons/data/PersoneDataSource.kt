package com.aymn.knowmeapp.persons.data

import android.content.ContentValues.TAG
import android.util.Log
import com.aymn.knowmeapp.network.model.PersonInformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import java.lang.reflect.Array.get

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

    override suspend fun getPersonsData(): Flow<List<PersonInformation>> = callbackFlow {
         db.collection("users").document("${auth.currentUser?.email}").collection("${auth.currentUser?.email}+persons")
        .addSnapshotListener { snap, e ->
            if (e != null){
                return@addSnapshotListener
            }
            val list = mutableListOf<PersonInformation>()

            snap?.documents?.forEach{
                if (it.exists()){
                    val person = it.toObject(PersonInformation::class.java)

                    list.add(person!!)
                    Log.d("TAG", "person: $it")
                } else {

                }
            }

            trySend(list)
        }
        awaitClose {

        }
    }
//
//    override suspend fun addPersonData(name: String): Flow<PersonInformation> = callbackFlow {
//        db.collection("users").document(auth.currentUser?.email.toString()).collection("${auth.currentUser?.email}+persons")
//            .document(name).get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    document.toObject(PersonInformation::class.java)
//                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
//                } else {
//                    Log.d("TAG", "No such document")
//                }
//            }
//
//        awaitClose {
//
//        }
//    }
//---------------------------------finish setPersonInformation--------------------------------------//
}