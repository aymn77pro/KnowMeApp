package com.aymn.knowmeapp.network

import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase

val db = Firebase.firestore
val auth = Firebase.auth
var dbFireStore = FirebaseFirestore.getInstance()
val docref: DocumentReference = dbFireStore.collection("users").document(auth.uid.toString())
interface FireStoreDatabase {

    private fun setupCacheSize() {
        // [START fs_setup_cache]
        val settings = firestoreSettings {
            cacheSizeBytes = FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
        }
        db.firestoreSettings = settings
        // [END fs_setup_cache]
    }
    fun addNewUserInfoOrUpdataInfo(userInfo: Map<String, Any>){
        docref.get().addOnSuccessListener {
            if (it.exists()){
                docref.update(userInfo)
            }else {

            }

        }
    }
    fun getUserInfo(){

            }
        }