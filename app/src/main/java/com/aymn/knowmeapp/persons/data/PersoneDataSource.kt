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

    //region save person details in fire store
    override suspend fun setPersonInformation(personInformation: PersonInformation, uri: Uri?) {
        if (uri == null) {
            updateUserInfo(personInformation, null)
        } else {
            upload(uri).collect {
                updateUserInfo(personInformation, it)
            }
        }
    }
    //endregion

    //region update person details
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
    //endregion

    //region show list of contact list
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
                    }
                }

                trySend(list)
            }
        awaitClose {

        }
    }
    //endregion

    //region show person details
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
    //endregion

    //region update person details
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

    //endregion

    //region delete person data from fire store
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
    //endregion

    //region show only imported people
    override suspend fun getImportedList(): Flow<List<PersonInformation>> = callbackFlow {
        db.collection("users").document("${auth.currentUser?.email}")
            .collection("${auth.currentUser?.email}+persons")
            .whereEqualTo("imported",true)
            .addSnapshotListener { snap, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                val list = mutableListOf<PersonInformation>()

                snap?.documents?.forEach {
                    if (it.exists()) {
                        val person = it.toObject(PersonInformation::class.java)
                        list.add(person!!)
                        Log.d("TAG", "getImportedList: ${list.size}")
                    }
                }
                trySend(list)
            }
        awaitClose {}
    }
    //endregion

//---------------------------------finish setPersonInformation--------------------------------------//
    // upload image to fire base storage
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
