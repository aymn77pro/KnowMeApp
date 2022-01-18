package com.aymn.knowmeapp.userInfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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


class UserEditInnfoFragment : Fragment() {

    private var _binding: UserProfileBinding? = null
    private val binding get() = _binding!!

//    private val REQUEST_CODE = 100
//    lateinit var fileImage: Uri

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

        auth = Firebase.auth

        viewModel.getUserInfo()
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
        Glide.with(requireContext()).load(Firebase.auth.currentUser?.photoUrl)
            .placeholder(R.drawable.loading_animation)
            .circleCrop()
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(binding?.userImage!!)


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