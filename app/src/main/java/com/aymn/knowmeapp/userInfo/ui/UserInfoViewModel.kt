package com.aymn.knowmeapp.userInfo.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aymn.knowmeapp.network.model.UserInformation
import com.aymn.knowmeapp.userInfo.domain.GetUserInfoUseCase
import com.aymn.knowmeapp.userInfo.domain.SetUserInfoUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserInfoViewModel(private val setUserInfoUseCase: SetUserInfoUseCase, private val getUserInfoUseCase: GetUserInfoUseCase):ViewModel() {
var _user= MutableStateFlow(UserInformation())
    val user:StateFlow<UserInformation> = _user.asStateFlow()

    val name = MutableLiveData<String>("")
    val phone = MutableLiveData<String>("")
    val email = MutableLiveData<String>("")
    val LinkIn = MutableLiveData<String>("")
    val twitter = MutableLiveData<String>("")
    val faceBook = MutableLiveData<String>("")



    private fun getUserInfo(userInfo: UserInformation) {
        viewModelScope.launch {
            Log.e("TAG", "setUserInfo: i am in ViweModel", )
            setUserInfoUseCase.invoke(userInfo)
        }
    }
    fun setNewUserInfo(name:String, number:String, email:String, linkIn:String, twitter:String, faceBook:String){
        val userProfile = UserInformation(name, number, email, linkIn, twitter, faceBook)
        getUserInfo(userProfile)
    }
        fun getUserInfo(){
            viewModelScope.launch {
                getUserInfoUseCase.invoke()
                    .collect { userInfor->
                    _user.update {
                        userInfor
                    }
                    Log.d("TAG", "kkkkkkkkkk: ${userInfor}")
                }
            }
        }

    }
