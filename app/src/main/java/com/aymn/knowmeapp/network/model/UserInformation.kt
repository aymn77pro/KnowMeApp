package com.aymn.knowmeapp.network.model

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class UserInformation(
    var name : String ?= "",
    var number : String ?= "" ,
    var email : String ?= "" ,
    var linkIn : String ?= "" ,
    var twitter : String ?= "" ,
    var faceBook : String ?= ""
    )
