package com.aymn.knowmeapp.persons.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aymn.knowmeapp.ViewModelFactory
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentListOfPersonsBinding
import kotlinx.coroutines.launch

class ListOfPersonsFragment : Fragment() {

    private val viewModel:PersonViewModel by activityViewModels {
        ViewModelFactory()
    }
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

    override fun onOptionsItemSelected(item: MenuItem) =
        when(item.itemId){
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
        val adabter = PesrsonListAdabter(this.requireContext())
        viewModel.getPersonInfo()
        binding?.recyclerView?.adapter = adabter
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        viewModel.person.value.observe(viewLifecycleOwner,{
            it.let {
                adabter.submitList(it)
                    }
                })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.deletePersonInfo()
        _binding = null
    }


        }



