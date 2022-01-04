package com.aymn.knowmeapp.userInfo.domain

import android.net.Uri
import com.aymn.knowmeapp.network.model.UserInformation
import com.aymn.knowmeapp.userInfo.data.UserInfoRepository

class SetUserInfoUseCase(private val userInfoRepository: UserInfoRepository) {

    suspend operator fun invoke(userInfo: UserInformation,uri: Uri) = userInfoRepository.setUserInfo(userInfo,uri)

}