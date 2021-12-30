package com.aymn.knowmeapp.network.model

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class UserInformation(
    val name : String ="",
    val number : String = "",
    val email : String= "",
    val linkIn : String = "",
    val twitter : String = "",
    val faceBook : String = ""
    )
