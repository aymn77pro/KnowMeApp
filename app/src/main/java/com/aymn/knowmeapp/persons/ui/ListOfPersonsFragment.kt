package com.aymn.knowmeapp.persons.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentListOfPersonsBinding

class ListOfPersonsFragment : Fragment() {

    private var _binding: FragmentListOfPersonsBinding?= null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOfPersonsBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =when(item.itemId){
        R.id.userProfile -> {
            val actionUserProfile = ListOfPersonsFragmentDirections.actionListOfPersonsFragmentToUserInfoFragment()
            findNavController().navigate(actionUserProfile)
        true
        }
        else -> false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.addParsone?.setOnClickListener {
            val action = ListOfPersonsFragmentDirections.actionListOfPersonsFragmentToEditParsoneInfoFragment()
            findNavController().navigate(action)
        }


    }


        }



