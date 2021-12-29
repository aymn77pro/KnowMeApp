package com.aymn.knowmeapp.contacts

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentListOfContactBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListOfContactFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentListOfContactBinding?= null
    private val binding get() = _binding

    var dbFireStore = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOfContactBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =when(item.itemId){
        R.id.userProfile -> {
            val actionUserProfile = ListOfContactFragmentDirections.actionListOfContactFragmentToUserInfoFragment()
            findNavController().navigate(actionUserProfile)
        true
        }
        else -> false
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            }


        }



