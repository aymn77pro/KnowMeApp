package com.aymn.knowmeapp.network.model

import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.firestore.GeoPoint

data class PersonInformation(
    var Name: String = "",
    var Number: String = "",
    var Email: String = "",
    var linkIn: String = "",
    var twitter: String = "",
    var faceBook: String = "",
    var personInformation: String = "",
    var id: String = "",
    var imageUri: String = "",
    var lattLoac : String?= "",
    var longLoca : String? = ""
)