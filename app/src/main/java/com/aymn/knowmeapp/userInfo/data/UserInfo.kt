package com.aymn.knowmeapp.userInfo.data

import com.aymn.knowmeapp.network.model.UserInformation
import kotlinx.coroutines.flow.Flow

interface UserInfo {
 // save user information in fire store
    suspend fun setUserInfo(userInfo: UserInformation)
// show user information
    suspend fun getUserInfo(): Flow<UserInformation>
}