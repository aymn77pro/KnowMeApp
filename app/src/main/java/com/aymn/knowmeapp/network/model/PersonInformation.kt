package com.aymn.knowmeapp.network.model

data class PersonInformation(
    var Name: String = "",
    var Number: String = "",
    var Email: String = "",
    var linkIn: String = "",
    var twitter: String = "",
    var faceBook: String = "",
    var personInformation: String = "",
    var id: String = ""
)

var personDataItem: MutableList<PersonInformation> = mutableListOf()
