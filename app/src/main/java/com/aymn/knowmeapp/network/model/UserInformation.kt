package com.aymn.knowmeapp.network.model

import android.net.Uri
import androidx.core.net.toUri

data class UserInformation(
    var name: String? = "",
    var number: String? = "",
    var email: String? = "",
    var linkIn: String? = "",
    var twitter: String? = "",
    var faceBook: String? = "",
    var profile:String ?="",
    var file:String=""
)
