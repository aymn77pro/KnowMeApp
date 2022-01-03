package com.aymn.knowmeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aymn.knowmeapp.persons.ui.PersonViewModel
import com.aymn.knowmeapp.userInfo.ui.UserInfoViewModel
import com.aymn.knowmeapp.utils.*
import java.lang.IllegalArgumentException

//
class ViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            @Suppress("UNCHEKED_CAST")
            return UserInfoViewModel(providerSetUserInfoUseCase(), providerGetUserInfoUseCase()) as T
        } else if (modelClass.isAssignableFrom(PersonViewModel::class.java)){
            @Suppress("UNCHEKED_CAST")
            return PersonViewModel(providerSetPersonDataUseCase(),providerGetPersonDataUseCase(),
                providerGetOnePersonDataUseCase(), providerSetOnePersonDataUseCase() ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
