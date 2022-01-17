package com.aymn.knowmeapp.network.model

import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.firestore.GeoPoint

data class PersonInformation(
    var Name: String = "",
    var Number: String = "",
    var Email: String = "",
    val linkIn: String = "",
    val twitter: String = "",
    val faceBook: String = "",
    val personInformation: String = "",
    var id: String = "",
    var imageUri: String = "",
    val lattLoac : String?= "",
    val longLoca : String? = "",
    var imported : Boolean? = false
)