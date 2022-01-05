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
    //---------------------------------setUserInfo-----------------------------------------------//
    val db = firebaseDB
    val auth = Firebase.auth
    override suspend fun setUserInfo(userInfo: UserInformation) {
        val user = db.collection("users").document("${auth.currentUser?.email}")
        Log.d("TAG", "image reserves: ${userInfo.profile}")
        uploadImage(userInfo.profile!!).collect {
            userInfo.profile = it
            user.set(userInfo, SetOptions.merge()).addOnCompleteListener {
                Log.d("TAG", "setUserInfo: ${it.isSuccessful}")
            }.addOnFailureListener {
                Log.e("TAG", "image dont save: ${userInfo.profile} ", )
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
            awaitClose {}
        }

        suspend fun uploadImage(file: Uri): Flow<Uri> = callbackFlow {
            val profileImage = UUID.randomUUID().toString()
            val storag = FirebaseStorage.getInstance().getReference("/Image/$profileImage")

            storag.putFile(file).addOnSuccessListener {
                Log.e("TAG", "uploadImage: done uploed ", )
               val dawnloadUri = storag.downloadUrl
                  dawnloadUri.addOnSuccessListener {
                   val uri = it
                    Log.d("TAG", "uploadImage: $it ")

                }.addOnFailureListener {
                    Log.e("TAG", "image failed to upload: ${it.message} ", )
                }
                trySend(dawnloadUri.result)
            }
            awaitClose {}
        }
    }