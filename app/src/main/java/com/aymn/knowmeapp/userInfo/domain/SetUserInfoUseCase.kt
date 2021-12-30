package com.aymn.knowmeapp.userInfo.domain

import com.aymn.knowmeapp.network.model.UserInformation
import com.aymn.knowmeapp.userInfo.data.UserInfoRepository

class SetUserInfoUseCase(private val userInfoRepository: UserInfoRepository) {

    suspend operator fun invoke(userInfo: UserInformation) = userInfoRepository.setUserInfo(userInfo)

}