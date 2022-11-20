package com.aymn.knowmeapp.userInfo.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.aymn.knowmeapp.ViewModelFactory
import com.bumptech.glide.Glide
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.UserProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.launch


class UserEditInnfoFragment : Fragment() {

    private var _binding: UserProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserInfoViewModel by activityViewModels {
        ViewModelFactory()
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UserProfileBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region user menu
        binding?.topAppBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.singOutIcon -> {
                    singOut()
                    val actionUserProfile =
                        UserEditInnfoFragmentDirections.actionUserEditInnfoFragmentToSingInFragment()
                    findNavController().navigate(actionUserProfile)
                    true
                }
                R.id.setting -> {
                    findNavController().navigate(UserEditInnfoFragmentDirections.actionUserEditInnfoFragmentToSettingsFragment())
                    true
                }
                else -> false
            }

        }
        //endregion

        auth = Firebase.auth

        //repeat in Resumed
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getUserInfo()
            }
        }

        viewModel.getUserFrindList()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_clint_id))
            .requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        viewModel.user.observe(viewLifecycleOwner, {
            binding.user = it
            binding.executePendingBindings()
        })


        viewModel.userFriendList.observe(viewLifecycleOwner, {
            binding?.countContact?.setText(it.size.toString())
        })

        //i use image from user google account
        Glide.with(requireContext()).load(Firebase.auth.currentUser?.photoUrl)
            .placeholder(R.drawable.loading_animation)
            .circleCrop()
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(binding?.userImage!!)

        //upload new or edit data
        binding?.update?.setOnClickListener {
            binding.apply {
                viewModel.setNewUserInfo(
                    Name.text.toString(),
                    userNumber.text.toString(),
                    email.text.toString(),
                    linkIn.text.toString(),
                    twitter.text.toString(),
                    faceBook.text.toString()
                )
            }
            val action =
                UserEditInnfoFragmentDirections.actionUserEditInnfoFragmentToListOfPersonsFragment()
            findNavController().navigate(action)
        }

        // to save user information in QR Code
        viewModel.user.observe(viewLifecycleOwner, { UserInfo ->
            binding?.qrGnarete?.setOnClickListener {
                if (UserInfo.name.isNullOrBlank()) {
                    Toast.makeText(context, "Name or Number or Email is empty", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val writer = QRCodeWriter()
                    try {
                        val value = Gson().toJson(UserInfo)
                        val bitMatrix = writer.encode(value, BarcodeFormat.QR_CODE, 512, 512)

                        val hight = bitMatrix.height
                        val width = bitMatrix.width
                        val bmp = Bitmap.createBitmap(width, hight, Bitmap.Config.RGB_565)
                        for (x in 0 until width) {
                            for (y in 0 until hight) {
                                bmp.setPixel(
                                    x,
                                    y,
                                    if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
                                )
                            }
                        }
                        binding?.qrCodeImage?.setImageBitmap(bmp)
                    } catch (e: WriterException) {
                        e.printStackTrace()
                    }
                }

            }

            //auto update value with out create fragment again for name and  number
            binding?.Name?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    binding?.name?.setText(s)
                }

                override fun afterTextChanged(s: Editable?) {}

            })
        })
        binding?.userNumber?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                binding?.number?.setText(s)
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }


    private fun singOut() {
        Firebase.auth.signOut()
        binding.apply {
            Name.text = null
            userNumber.text = null
            email.text = null
            linkIn.text = null
            twitter.text = null
            faceBook.text = null
        }
        googleSignInClient.signOut()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}