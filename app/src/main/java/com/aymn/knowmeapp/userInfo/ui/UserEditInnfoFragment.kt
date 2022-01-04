package com.aymn.knowmeapp.userInfo.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aymn.knowmeapp.ViewModelFactory
import com.aymn.knowmeapp.network.model.UserInformation
import com.bumptech.glide.Glide
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentUserEditInnfoBinding


class UserEditInnfoFragment : Fragment() {

    private var _binding: FragmentUserEditInnfoBinding? = null
    private val binding get() = _binding

    private val REQUEST_CODE = 100
    lateinit var fileImage: Uri

    private val viewModel: UserInfoViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserEditInnfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.Name?.setText(viewModel.user.value?.name)
        binding?.number?.setText(viewModel.user.value?.number)
        binding?.Email?.setText(viewModel.user.value?.email)
        binding?.LinkIn?.setText(viewModel.user.value?.linkIn)
        binding?.Twitter?.setText(viewModel.user.value?.twitter)
        binding?.FaceBook?.setText(viewModel.user.value?.faceBook)
        fileImage ="".toUri()
        if(viewModel.user.value?.file.isNullOrBlank()){
            binding?.personImage?.setImageResource(R.drawable.ic_baseline_account_circle_24)

        }else{
            binding?.personImage?.setImageURI(viewModel.user.value!!.profile?.toUri())
            Glide.with(requireContext()).load(viewModel.user.value!!.profile?.toUri())
                .into(binding?.personImage!!)
        }
        binding?.personImage?.setOnClickListener {
            openGalleryForImage()
        }


        binding?.save?.setOnClickListener {
            viewModel.setNewUserInfo(
                binding?.Name?.text.toString(),
                binding?.number?.text.toString(),
                binding?.Email?.text.toString(),
                binding?.LinkIn?.text.toString(),
                binding?.Twitter?.text.toString(),
                binding?.FaceBook?.text.toString(),
                "",fileImage

            )
            Log.d("TAG", "fileImage: $fileImage ")

            val action =
                UserEditInnfoFragmentDirections.actionUserEditInnfoFragmentToUserInfoFragment()
            findNavController().navigate(action)

        }

    }



    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            binding?.personImage?.setImageURI(data?.data) // handle chosen image
            fileImage = data?.data!!
            Log.d("TAG", "fileimag = ${fileImage}")
        }
    }

}