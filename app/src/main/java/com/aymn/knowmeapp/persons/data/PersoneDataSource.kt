package com.aymn.knowmeapp.persons.data

import android.util.Log
import com.aymn.knowmeapp.network.model.PersonInformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class PersoneDataSource(
    private val firebaseDB : FirebaseFirestore
):PersonData {
    val db = firebaseDB
    val auth = Firebase.auth.currentUser?.email
//-----------------------------------start setPersonInformation-------------------------------------//
    override suspend fun setPersonInformation(personInformation: PersonInformation) {
     val document = db.collection("users").document("$auth").collection("persons").document()
    personInformation.id = document.id
        val set = document.set(personInformation)
        set.addOnCompleteListener {
            Log.e("TAG", "${it.isSuccessful}: Successful")
        }
        set.addOnFailureListener {
            Log.e("TAG", "${it.message}: filed ")
        }
    }
//---------------------------------finish setPersonInformation--------------------------------------//


}