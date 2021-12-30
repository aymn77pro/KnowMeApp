package com.aymn.knowmeapp.userInfo.data
import android.util.Log
import com.aymn.knowmeapp.network.model.UserInformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class UserInfoDataSource(
    private val firebaseDB: FirebaseFirestore
    ) : UserInfo {
//--------------------------------------------------------------------------------//
    val db = firebaseDB
    val auth = Firebase.auth.currentUser?.email
    override suspend fun setUserInfo(userInfo: UserInformation){

        db.collection("users").document("${auth}").set(userInfo).addOnCompleteListener {
            Log.d("TAG", "setUserInfo: ${it.isSuccessful}")
        }.addOnFailureListener {  }
    }
//---------------------------------------------------------------------------------------------------------------------//


    override suspend fun getUserInfo(): Flow<UserInformation> = callbackFlow {

        db.collection("users").document("${auth}").get()
            .addOnSuccessListener {
                val userInfo = it.toObject(UserInformation::class.java)
                if (userInfo != null){
                    trySend(userInfo)

                }else if (userInfo == null){
                    Log.e("TAG","why null:${userInfo}")
                }
            }.addOnCompleteListener{
                Log.e("TAG", "Complete")
            }
             .addOnFailureListener {
                Log.e("TAG", "Filed")
             }
        awaitClose{

        }
    }
}