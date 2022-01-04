package com.aymn.knowmeapp.userInfo.data

import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import com.aymn.knowmeapp.network.model.UserInformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import java.util.*


class UserInfoDataSource(
    private val firebaseDB: FirebaseFirestore,
) : UserInfo {
    //---------------------------------setUserInfo-----------------------------------------------//
    val db = firebaseDB
    val auth = Firebase.auth
    override suspend fun setUserInfo(userInfo: UserInformation,uri:Uri) {
        uploadImage(uri).collect{
          //  userInfo.file = uri.toString()
            Log.d("TAG", "setUserInfo: ${auth.currentUser?.email}", )

       val user= db.collection("users").document("${auth.currentUser?.email}")
            userInfo.profile = it.toString()
            Log.d("TAG", "profile value = ${userInfo.profile}: ")
            user.set(userInfo, SetOptions.merge()).addOnCompleteListener {
            Log.d("TAG", "setUserInfo: ${it.isSuccessful}")
        }.addOnFailureListener {

            }

    }
    }
    //---------------------------------------------------------------------------------------------------------------------//
    override suspend fun getUserInfo(): Flow<UserInformation> = callbackFlow {
        db.collection("users").document("${auth.currentUser?.email}").get()
            .addOnSuccessListener {
                val userInfo = it.toObject(UserInformation::class.java)
                if (userInfo != null) {
                    Log.d("TAG", "pic value: ${userInfo.profile}")
                    trySend(userInfo)
                } else if (userInfo == null) {
                    Log.e("TAG", "why null:${userInfo}")
                }
            }.addOnCompleteListener {
                Log.e("TAG", "Complete")
            }
            .addOnFailureListener {
                Log.e("TAG", "Filed")
            }
        awaitClose {

        }
    }
    suspend fun uploadImage(file:Uri):Flow<Uri> = callbackFlow {
    val profileImage = UUID.randomUUID().toString()
        val storag = FirebaseStorage.getInstance().getReference("/Image/$profileImage")
        storag.putFile(file).addOnSuccessListener {
            storag.downloadUrl.addOnSuccessListener {
                Log.d("TAG", "uploadImage: $it ")
                trySend(it)
            }
        }
        awaitClose{}
    }

}