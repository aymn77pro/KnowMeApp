package com.aymn.knowmeapp.userInfo.data

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.aymn.knowmeapp.network.model.UserInformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*
//--------------------------------------------------
class UserInfoDataSource(
    private val firebaseDB: FirebaseFirestore,
) : UserInfo {
    //region save user information
    //---------------------------------setUserInfo-----------------------------------------------//
    val db = firebaseDB
    val auth = Firebase.auth

    override suspend fun setUserInfo(userInfo: UserInformation) {

            val user = db.collection("users").document("${auth.currentUser?.email}")
            user.set(userInfo).addOnCompleteListener {
                Log.d("TAG", "setUserInfo: ${it.isSuccessful}")
            }.addOnFailureListener {
                Log.e("TAG", "image dont save: ${userInfo} ",)
            }
        }
//endregion

    //region show user information
        override suspend fun getUserInfo(): Flow<UserInformation> = callbackFlow {
            db.collection("users").document("${auth.currentUser?.email}").get()
                .addOnSuccessListener {
                    val userInfo = it.toObject(UserInformation::class.java)
                    if (userInfo != null) {
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
            awaitClose {}
        }
    //endregion

}

