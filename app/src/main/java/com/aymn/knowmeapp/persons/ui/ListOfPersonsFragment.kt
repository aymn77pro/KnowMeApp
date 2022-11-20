package com.aymn.knowmeapp.persons.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.aymn.knowmeapp.ViewModelFactory
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentListOfPersonsBinding
import kotlinx.coroutines.launch

class ListOfPersonsFragment : Fragment() {

    private val viewModel: PersonViewModel by activityViewModels {
        ViewModelFactory()
    }
    private var _binding: FragmentListOfPersonsBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOfPersonsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adabter = PesrsonListAdabter(this.requireContext())
        binding?.recyclerView?.adapter = adabter

        viewModel.getImportedList()
        //for menu and the icon
        binding?.topAppBar?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.userProfile -> {
                    val actionUserProfile =
                        ListOfPersonsFragmentDirections.actionListOfPersonsFragmentToUserEditInnfoFragment2()
                    findNavController().navigate(actionUserProfile)
                    true
                }
                R.id.importedList -> {
                    viewModel.personsImported.observe(viewLifecycleOwner, {
                        it.let {
                            adabter.submitList(it)
                        }
                    })
                    true
                }
                R.id.showAll -> {
                    viewModel.persons.observe(viewLifecycleOwner, {
                        it.let {
                            adabter.submitList(it)
                        }
                    })
                    true
                }
                else -> false
            }
        }
        // Give the adapter your list of contact
        viewModel.persons.observe(viewLifecycleOwner, { personList ->

            adabter.submitList(personList)

        })
        binding?.addParsone?.setOnClickListener {
            val action =
                ListOfPersonsFragmentDirections.actionListOfPersonsFragmentToParsoneInfoFragment(
                    name = "Add New Person"
                )
            findNavController().navigate(action)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getPersonData()
            }
        }

        binding?.search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.search(it)
                }
                return true

            }


        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}