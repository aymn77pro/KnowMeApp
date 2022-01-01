package com.aymn.knowmeapp.userInfo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aymn.knowmeapp.ViewModelFactory
import com.example.knowmeapp.databinding.FragmentUserEditInnfoBinding


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

        viewModel.name.value = binding?.Name?.text.toString()
        binding?.Name?.setText(viewModel.user.value.name)
        binding?.number?.setText(viewModel.user.value.number)
        binding?.Email?.setText(viewModel.user.value.email)
        binding?.LinkIn?.setText(viewModel.user.value.linkIn)
            binding?.Twitter?.setText(viewModel.user.value.twitter)
        binding?.FaceBook?.setText(viewModel.user.value.faceBook)


        binding?.save?.setOnClickListener {
            viewModel.setNewUserInfo(binding?.Name?.text.toString(), binding?.number?.text.toString(), binding?.Email?.text.toString(),
                  binding?.LinkIn?.text.toString(), binding?.Twitter?.text.toString(),binding?.FaceBook?.text.toString())

            val action = UserEditInnfoFragmentDirections.actionUserEditInnfoFragmentToUserInfoFragment()
            findNavController().navigate(action)

        }

    }
}