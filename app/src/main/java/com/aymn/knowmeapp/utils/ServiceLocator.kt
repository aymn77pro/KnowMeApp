package com.aymn.knowmeapp.utils

import com.aymn.knowmeapp.userInfo.data.UserInfoDataSource
import com.aymn.knowmeapp.userInfo.data.UserInfoRepository
import com.aymn.knowmeapp.userInfo.domain.GetUserInfoUseCase
import com.aymn.knowmeapp.userInfo.domain.SetUserInfoUseCase
import com.google.firebase.firestore.FirebaseFirestore
// user profile
fun providerUserInfoDataSource():UserInfoDataSource = UserInfoDataSource(FirebaseFirestore.getInstance())

fun providerUserInfoRepository():UserInfoRepository = UserInfoRepository(providerUserInfoDataSource())

fun providerSetUserInfoUseCase():SetUserInfoUseCase = SetUserInfoUseCase(providerUserInfoRepository())

fun providerGetUserInfoUseCase():GetUserInfoUseCase = GetUserInfoUseCase(providerUserInfoRepository())
