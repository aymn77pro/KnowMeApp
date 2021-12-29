package com.aymn.knowmeapp.userInfo.data

import com.aymn.knowmeapp.network.model.UserInformation

class UserInfoRepository(private val userInfoDataSource: UserInfoDataSource) {

    suspend fun setUserInfo(userInfo: UserInformation) = userInfoDataSource.setUserInfo(userInfo)

    suspend fun getUserInfo() = userInfoDataSource.getUserInfo()

}