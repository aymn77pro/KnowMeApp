package com.aymn.knowmeapp.userInfo.domain

import com.aymn.knowmeapp.userInfo.data.UserInfoRepository

class GetUserInfoUseCase(private val userInfoRepository: UserInfoRepository) {

    suspend operator fun invoke() = userInfoRepository.getUserInfo()

}