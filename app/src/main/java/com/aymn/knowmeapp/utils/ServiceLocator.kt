package com.aymn.knowmeapp.utils

import com.aymn.knowmeapp.persons.data.PersonRepository
import com.aymn.knowmeapp.persons.data.PersoneDataSource
import com.aymn.knowmeapp.persons.domain.AddPersonUseCase
import com.aymn.knowmeapp.persons.domain.GetPersonDataUseCase
import com.aymn.knowmeapp.persons.domain.SetPersonDataUseCase
import com.aymn.knowmeapp.userInfo.data.UserInfoDataSource
import com.aymn.knowmeapp.userInfo.data.UserInfoRepository
import com.aymn.knowmeapp.userInfo.domain.GetUserInfoUseCase
import com.aymn.knowmeapp.userInfo.domain.SetUserInfoUseCase
import com.google.firebase.firestore.FirebaseFirestore
//---------------------start user profile---------------------------------------------------------------//
fun providerUserInfoDataSource():UserInfoDataSource = UserInfoDataSource(FirebaseFirestore.getInstance())

fun providerUserInfoRepository():UserInfoRepository = UserInfoRepository(providerUserInfoDataSource())

fun providerSetUserInfoUseCase():SetUserInfoUseCase = SetUserInfoUseCase(providerUserInfoRepository())

fun providerGetUserInfoUseCase():GetUserInfoUseCase = GetUserInfoUseCase(providerUserInfoRepository())

//--------------------finish user profile-------------------------------------------------------------//

fun providerPersonDataSource():PersoneDataSource = PersoneDataSource(FirebaseFirestore.getInstance())

fun providerPersonRepository():PersonRepository = PersonRepository(providerPersonDataSource())

fun providerSetPersonDataUseCase():SetPersonDataUseCase = SetPersonDataUseCase(providerPersonRepository())

fun providerGetPersonDataUseCase():GetPersonDataUseCase = GetPersonDataUseCase(providerPersonRepository())

fun providerAddPersonDataUseCase():AddPersonUseCase = AddPersonUseCase(providerPersonRepository())