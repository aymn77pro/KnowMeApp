package com.aymn.knowmeapp.userInfo.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.aymn.knowmeapp.ViewModelFactory
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentUserInfoBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserInfoFragment : Fragment() {
    private val viewModel:UserInfoViewModel by activityViewModels {
        ViewModelFactory()
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private var _binding: FragmentUserInfoBinding?= null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUserInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserInfoBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.user.value.name.isBlank()){
            val action = UserInfoFragmentDirections.actionUserInfoFragmentToUserEditInnfoFragment()
            findNavController().navigate(action)
        }

        auth = Firebase.auth

//        if (viewModel.user.value.name == null){
//            val action = UserInfoFragmentDirections.actionUserInfoFragmentToUserEditInnfoFragment()
//            findNavController().navigate(action)
//        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
             .requestIdToken(getString(R.string.web_clint_id))
            .requestEmail().build()
        viewModel.getUserInfo()
        googleSignInClient = GoogleSignIn.getClient(requireContext(),gso)

        Log.e("TAG", "onViewCreated: ${viewModel.user.value.name}", )

        binding?.singOut?.setOnClickListener {
            singOut()
        }
        binding?.editUserInfo?.setOnClickListener {
            val action = UserInfoFragmentDirections.actionUserInfoFragmentToUserEditInnfoFragment()
            findNavController().navigate(action)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.user.collect{
                    binding?.userName?.setText(viewModel.user.value.name)
                    binding?.userEmail?.setText(viewModel.user.value.email)
                    binding?.userPhone?.setText(viewModel.user.value.number)
                    binding?.LinkIn?.setText(viewModel.user.value.linkIn)
                    binding?.twitter?.setText(viewModel.user.value.twitter)
                    binding?.faceBook?.setText(viewModel.user.value.faceBook)
                }
            }
        }
    }
    private fun singOut(){
        Firebase.auth.signOut()
        googleSignInClient.signOut()
        val action = UserInfoFragmentDirections.actionUserInfoFragmentToSingInFragment()
        findNavController().navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
