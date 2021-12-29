package com.aymn.knowmeapp.userInfo.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.aymn.knowmeapp.ViewModelFactory
import com.aymn.knowmeapp.network.model.UserInformation
import com.aymn.knowmeapp.userInfo.data.UserInfo
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentUserEditInnfoBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timerTask


class UserEditInnfoFragment : Fragment() {
    private var _binding : FragmentUserEditInnfoBinding ?= null
    private val binding get() = _binding

    private val viewModel : UserInfoViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserEditInnfoBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserInfo()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.user.collect{

                }
            }
        }
//        //if (binding?.Name?.text.toString().isBlank()){
//            binding?.Name?.setText(Firebase.auth.currentUser?.displayName)
//        }
//        //if (binding?.Email?.text!!.isBlank()){
//            binding?.Email?.setText(Firebase.auth.currentUser?.email)
//        }



        binding?.button?.setOnClickListener {
            viewModel.getUserInfo(binding?.Name?.text.toString(), binding?.Number?.text.toString(), binding?.Email?.text.toString(),
                  binding?.LinkIn?.text.toString(), binding?.Twitter?.text.toString(),binding?.FaceBook?.text.toString())

            val action = UserEditInnfoFragmentDirections.actionUserEditInnfoFragmentToUserInfoFragment()
            findNavController().navigate(action)

        }

    }
}