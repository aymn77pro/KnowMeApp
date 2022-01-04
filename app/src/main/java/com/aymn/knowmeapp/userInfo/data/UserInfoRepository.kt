package com.aymn.knowmeapp.userInfo.data

import android.net.Uri
import com.aymn.knowmeapp.network.model.UserInformation

class UserInfoRepository(private val userInfoDataSource: UserInfoDataSource) {

    suspend fun setUserInfo(userInfo: UserInformation,uri: Uri) = userInfoDataSource.setUserInfo(userInfo,uri)

    suspend fun getUserInfo() = userInfoDataSource.getUserInfo()

}