package com.aymn.knowmeapp.userInfo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.aymn.knowmeapp.ViewModelFactory
import com.bumptech.glide.Glide
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentUserInfoBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class UserInfoFragment : Fragment() {

//    private val viewModel: UserInfoViewModel by activityViewModels {
//        ViewModelFactory()
//    }
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var googleSignInClient: GoogleSignInClient
//
//    private var _binding: FragmentUserInfoBinding? = null
//    private val binding get() = _binding
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentUserInfoBinding.inflate(inflater, container, false)
//        setHasOptionsMenu(true)
//        return binding?.root
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.sing_out, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem) =
//        when (item.itemId) {
//            R.id.singOutIcon -> {
//                singOut()
//                val actionUserProfile =
//                    UserInfoFragmentDirections.actionUserInfoFragmentToSingInFragment()
//                findNavController().navigate(actionUserProfile)
//                true
//            }
//            R.id.setting ->{ findNavController().navigate(UserInfoFragmentDirections.actionUserInfoFragmentToSettingsFragment())
//            true}
//            else -> false
//        }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        auth = Firebase.auth
//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.web_clint_id))
//            .requestEmail().build()
//
//        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
//
//        binding?.back?.setOnClickListener {
//            val action = UserInfoFragmentDirections.actionUserInfoFragmentToListOfContactFragment()
//            findNavController().navigate(action)
//        }
//        binding?.editUserInfo?.setOnClickListener {
//            val action = UserInfoFragmentDirections.actionUserInfoFragmentToUserEditInnfoFragment()
//            findNavController().navigate(action)
//        }
//        viewModel.getUserInfo()
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.RESUMED) {
//                viewModel.getUserInfo()
//
//                viewModel.user.observe(viewLifecycleOwner, {
//
//                    binding?.userName?.setText(viewModel.user.value?.name)
//                    binding?.userEmail?.setText(viewModel.user.value?.email)
//                    binding?.userPhone?.setText(viewModel.user.value?.number)
//                    binding?.LinkIn?.setText(viewModel.user.value?.linkIn)
//                    binding?.twitter?.setText(viewModel.user.value?.twitter)
//                    binding?.faceBook?.setText(viewModel.user.value?.faceBook)
//
//                        Glide.with(requireContext()).load(auth.currentUser?.photoUrl)
//                            .placeholder(R.drawable.loading_animation)
//                            .error(R.drawable.ic_baseline_account_circle_24)
//                            .into(binding?.userPic!!)
//                })
//
//            }
//
//
//        }
//    }
//
//    private fun singOut() {
//        Firebase.auth.signOut()
//        viewModel.user.value?.name=null
//        viewModel.user.value?.email = null
//        viewModel.user.value?.number = null
//        viewModel.user.value?.linkIn = null
//        viewModel.user.value?.twitter = null
//        viewModel.user.value?.faceBook = null
//
//        googleSignInClient.signOut()
//    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

}
