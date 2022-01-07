package com.aymn.knowmeapp.userInfo.ui

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aymn.knowmeapp.network.model.UserInformation
import com.aymn.knowmeapp.userInfo.domain.GetUserInfoUseCase
import com.aymn.knowmeapp.userInfo.domain.SetUserInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val setUserInfoUseCase: SetUserInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
    private var _user = MutableStateFlow(UserInformation())
    val user = _user.asLiveData()

   private fun setUserInfo(userInfo: UserInformation) {
        viewModelScope.launch {
            Log.e("TAG", "setUserInfo: i am in set viewModel $userInfo")
            setUserInfoUseCase.invoke(userInfo)
            Log.d("TAG", "ttttttttttt: ${userInfo}")
        }
    }
    fun setNewUserInfo(
        name: String,
        number: String,
        email: String,
        linkIn: String,
        twitter: String,
        faceBook: String
    )
    {
        val userProfile = UserInformation(name, number, email, linkIn, twitter, faceBook)
        setUserInfo(userProfile)
    }

    fun getUserInfo() {
        viewModelScope.launch {
            getUserInfoUseCase.invoke()
                .collect { userInfor ->
                    _user.update {
                        userInfor
                    }
                    Log.d("TAG", "kkkkkkkkkk: ${userInfor}")
                }
        }
    }
}
