package com.aymn.knowmeapp.persons.data

import android.net.Uri
import android.util.Log
import com.aymn.knowmeapp.network.model.PersonInformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*

class PersoneDataSource(
    private val firebaseDB: FirebaseFirestore
) : PersonData {
    val db = firebaseDB
    val auth = Firebase.auth

    //-----------------------------------start setPersonInformation-------------------------------------//
    override suspend fun setPersonInformation(personInformation: PersonInformation, uri: Uri?) {
        if (uri == null) {
            updateUserInfo(personInformation, null)
        } else {
            upload(uri).collect {
                updateUserInfo(personInformation, it)
            }
        }
    }

    fun updateUserInfo(personInformation: PersonInformation, uri: Uri?) {
        val document = db.collection("users").document("${auth.currentUser?.email}")
            .collection("${auth.currentUser?.email}+persons").document()

        personInformation.id = document.id
        uri?.let {
            personInformation.imageUri = it.toString()
        }

        Log.d("TAG", "id: ${personInformation.id}")
        document.set(personInformation)
    }

    override suspend fun getPersonsData(): Flow<List<PersonInformation>> = callbackFlow {

        db.collection("users").document("${auth.currentUser?.email}")
            .collection("${auth.currentUser?.email}+persons")
            .addSnapshotListener { snap, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                val list = mutableListOf<PersonInformation>()

                snap?.documents?.forEach {
                    if (it.exists()) {
                        val person = it.toObject(PersonInformation::class.java)
                        list.add(person!!)
                    } else {
                    }
                }

                trySend(list)
            }
        awaitClose {

        }
    }

    override suspend fun getOnePersonData(id: String): Flow<PersonInformation> = callbackFlow {

        Log.d("TAG", "getOnePersonData: $id")
        val personDATA = db.collection("users").document(auth.currentUser?.email.toString())
            .collection("${auth.currentUser?.email}+persons")
            .document(id)

        personDATA.get()
            .addOnSuccessListener { document ->
                val item = document.toObject(PersonInformation::class.java)
                if (document != null && document.exists()) {
                    trySend(item!!)
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("TAG", "No such document")
                }
            }
        awaitClose {}
    }

    override suspend fun setOnePersonData(
        id: String,
        personInformation: PersonInformation,
        uri: Uri?
    ) {
        if (uri.toString().contains("https:")) {
            val document = db.collection("users").document(auth.currentUser?.email.toString())
                .collection("${auth.currentUser?.email}+persons")
                .document(id)
            personInformation.id = id
            document.set(personInformation, SetOptions.merge())
        } else {
            if (uri != null){
            upload(uri).collect {
                val document = db.collection("users").document(auth.currentUser?.email.toString())
                    .collection("${auth.currentUser?.email}+persons")
                    .document(id)
                personInformation.imageUri = it.toString()
                personInformation.id = id
                document.set(personInformation, SetOptions.merge())
                }

            } else {
                val document = db.collection("users").document(auth.currentUser?.email.toString())
                    .collection("${auth.currentUser?.email}+persons")
                    .document(id)
                personInformation.id = id
                document.set(personInformation, SetOptions.merge())
            }
        }
    }

    override suspend fun deletePersonData(id: String) {

        val document = db.collection("users").document(auth.currentUser?.email.toString())
            .collection("${auth.currentUser?.email}+persons")
            .document(id)

        document.delete().addOnCompleteListener {
            Log.d("TAG", "DocumentSnapshot successfully deleted!")
        }.addOnFailureListener {
            Log.d("TAG", "DocumentSnapshot dont deleted! ")
        }
    }

//---------------------------------finish setPersonInformation--------------------------------------//

    suspend fun upload(file: Uri): Flow<Uri> = callbackFlow {

        val firestore = UUID.randomUUID().toString()
        val storageRef = FirebaseStorage.getInstance().getReference("/images/$firestore")

        storageRef.putFile(file).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { imageUri ->
                Log.e("TAG", "imageUrl:${imageUri}")
                trySend(imageUri)

            }

        }

        awaitClose { }

    }

}
