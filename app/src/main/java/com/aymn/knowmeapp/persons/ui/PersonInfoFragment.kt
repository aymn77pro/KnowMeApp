package com.aymn.knowmeapp.persons.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aymn.knowmeapp.ViewModelFactory
import com.bumptech.glide.Glide
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentParsoneInfoBinding


class PersonInfoFragment : Fragment() {
    private val viewModel: PersonViewModel by activityViewModels {
        ViewModelFactory()
    }
    private val navigationArgs: PersonInfoFragmentArgs by navArgs()


    private var _binding: FragmentParsoneInfoBinding? = null
    val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentParsoneInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.getOnePerson(id)

        viewModel.personData.observe(viewLifecycleOwner, {
            bind()
        }
        )
        binding?.editPersonInfo?.setOnClickListener {
            val editPerson =
                PersonInfoFragmentDirections.actionParsoneInfoFragmentToEditParsoneInfoFragment(id,viewModel.personData.value!!.Name)
            findNavController().navigate(editPerson)
        }
        binding?.delete?.setOnClickListener {
            viewModel.deletePerson(id)
            val deletPersonData = PersonInfoFragmentDirections.actionParsoneInfoFragmentToListOfPersonsFragment()
            findNavController().navigate(deletPersonData)
        }

    }

    private fun bind() {
        binding?.name?.text = viewModel.personData.value?.Name.toString()
        binding?.email?.text = viewModel.personData.value?.Email
        binding?.number?.text = viewModel.personData.value?.Number
        binding?.linkIn?.text = viewModel.personData.value?.linkIn
        binding?.teitter?.text = viewModel.personData.value?.twitter
        binding?.faceBook?.text = viewModel.personData.value?.faceBook
        binding?.personInfo?.text = viewModel.personData.value?.personInformation
        Glide.with(requireContext()).load(viewModel.personData.value?.imageUri?.toUri())
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(binding?.personImage!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}