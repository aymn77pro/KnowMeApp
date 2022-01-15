package com.aymn.knowmeapp.userInfo.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.aymn.knowmeapp.network.model.UserInformation
import com.bumptech.glide.Glide
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentUserEditInnfoBinding
import com.example.knowmeapp.databinding.UserProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


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

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.sing_out, menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem) =
//        when (item.itemId) {
//            R.id.singOutIcon -> {
//                singOut()
//                val actionUserProfile =
//                    UserEditInnfoFragmentDirections.actionUserEditInnfoFragmentToSingInFragment()
//                findNavController().navigate(actionUserProfile)
//                true
//            }
//            R.id.setting ->{ findNavController().navigate(UserEditInnfoFragmentDirections.actionUserEditInnfoFragmentToSettingsFragment())
//                true}
//            else -> false
//        }


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
                R.id.setting ->{ findNavController().navigate(UserEditInnfoFragmentDirections.actionUserEditInnfoFragmentToSettingsFragment())
                    true}
                else -> false
            }

        }

        auth = Firebase.auth
        viewModel.getUserInfo()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_clint_id))
            .requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        viewModel.user.observe(viewLifecycleOwner,{
            binding.user=it
            binding.executePendingBindings()
        })

//        fileImage ="".toUri()

        Glide.with(requireContext()).load(Firebase.auth.currentUser?.photoUrl)
            .placeholder(R.drawable.loading_animation)
            .circleCrop()
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(binding?.userImage!!)


        binding?.update?.setOnClickListener {
            val value= viewModel.user.value
            viewModel.setNewUserInfo(
            value?.name.toString(),value?.number.toString(),value?.email.toString(),
                value?.linkIn.toString(),value?.twitter.toString(),value?.faceBook.toString()
            )
            val action = UserEditInnfoFragmentDirections.actionUserEditInnfoFragmentToListOfPersonsFragment()
            findNavController().navigate(action)
        }
    }

    private fun singOut() {
        Firebase.auth.signOut()
        viewModel.user.value?.name=null
        viewModel.user.value?.email = null
        viewModel.user.value?.number = null
        viewModel.user.value?.linkIn = null
        viewModel.user.value?.twitter = null
        viewModel.user.value?.faceBook = null

        googleSignInClient.signOut()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



//    private fun openGalleryForImage() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, REQUEST_CODE)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
//          //  binding?.personImage?.setImageURI(data?.data) // handle chosen image
//            fileImage = data?.data!!
//
//        }
//    }
}