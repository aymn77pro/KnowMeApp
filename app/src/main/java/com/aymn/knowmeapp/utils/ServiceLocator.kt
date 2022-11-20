package com.aymn.knowmeapp.utils

import com.aymn.knowmeapp.persons.data.PersonRepository
import com.aymn.knowmeapp.persons.data.PersoneDataSource
import com.aymn.knowmeapp.persons.domain.*
import com.aymn.knowmeapp.userInfo.data.UserInfoDataSource
import com.aymn.knowmeapp.userInfo.data.UserInfoRepository
import com.aymn.knowmeapp.userInfo.domain.GetUserInfoUseCase
import com.aymn.knowmeapp.userInfo.domain.SearchUseCase
import com.aymn.knowmeapp.userInfo.domain.SetUserInfoUseCase
import com.google.firebase.firestore.FirebaseFirestore

//---------------------start user profile---------------------------------------------------------------//
fun providerUserInfoDataSource(): UserInfoDataSource =
    UserInfoDataSource(FirebaseFirestore.getInstance())

fun providerUserInfoRepository(): UserInfoRepository =
    UserInfoRepository(providerUserInfoDataSource())

fun providerSetUserInfoUseCase(): SetUserInfoUseCase =
    SetUserInfoUseCase(providerUserInfoRepository())

fun providerGetUserInfoUseCase(): GetUserInfoUseCase =
    GetUserInfoUseCase(providerUserInfoRepository())

//--------------------finish user profile-------------------------------------------------------------//
//--------------------start person data--------------------------------------------------------------//
fun providerPersonDataSource(): PersoneDataSource =
    PersoneDataSource(FirebaseFirestore.getInstance())

fun providerPersonRepository(): PersonRepository = PersonRepository(providerPersonDataSource())

fun providerSetPersonDataUseCase(): SetPersonDataUseCase =
    SetPersonDataUseCase(providerPersonRepository())

fun providerGetPersonDataUseCase(): GetPersonDataUseCase =
    GetPersonDataUseCase(providerPersonRepository())

fun providerGetOnePersonDataUseCase(): GetOnePersonUseCase =
    GetOnePersonUseCase(providerPersonRepository())

fun providerSetOnePersonDataUseCase(): SetOnePersoneData =
    SetOnePersoneData(providerPersonRepository())

fun providerDeletePersonDataUseCase(): DeletePersonInformationUseCase =
    DeletePersonInformationUseCase(providerPersonRepository())

fun providerGetIportedListUseCase():GetImportedListUseCase =
    GetImportedListUseCase(providerPersonRepository())

fun providerSearchUseCase():SearchUseCase = SearchUseCase(providerPersonRepository())