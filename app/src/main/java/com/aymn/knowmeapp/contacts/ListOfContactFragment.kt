package com.aymn.knowmeapp.contacts

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentListOfContactBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ListOfContactFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private var _binding: FragmentListOfContactBinding?= null
    private val binding get() = _binding




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
    /*
    *
    * */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_clint_id))
            .requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(context,gso)
    }

    private fun singOut(){
        Firebase.auth.signOut()
        googleSignInClient.signOut()
        val action = ListOfContactFragmentDirections.actionListOfContactFragmentToSingInFragment()
        findNavController().navigate(action)
    }
}
