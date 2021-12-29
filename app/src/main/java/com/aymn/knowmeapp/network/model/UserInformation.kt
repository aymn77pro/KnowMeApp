package com.aymn.knowmeapp.network.model

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class UserInformation(
    val name : String = Firebase.auth.currentUser?.displayName.toString(),
    val number : String = "",
    val email : String= Firebase.auth.currentUser?.email.toString(),
    val linkIn : String = "",
    val twitter : String = "",
    val faceBook : String = ""
    )
