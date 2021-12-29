package com.aymn.knowmeapp.userInfo.data

import com.aymn.knowmeapp.network.model.UserInformation
import kotlinx.coroutines.flow.Flow

interface UserInfo {

    suspend fun setUserInfo(userInfo: UserInformation)

    suspend fun getUserInfo(): Flow<UserInformation>
}