package com.aymn.knowmeapp.persons.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aymn.knowmeapp.ViewModelFactory
import com.aymn.knowmeapp.network.model.PersonInformation
import com.example.knowmeapp.databinding.FragmentEditPersonInfoBinding

class EditPersonInfoFragment : Fragment() {


    private val viewModel: PersonViewModel by activityViewModels {
        ViewModelFactory()
    }

    private val navigationArgs: EditPersonInfoFragmentArgs by navArgs()

    private var _binding: FragmentEditPersonInfoBinding? = null
    val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditPersonInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        if (id != "empty") {
            val x = viewModel.personData.value
            binding?.personeName?.setText(x?.Name)
            binding?.personeNumber?.setText(x?.Number)
            binding?.personEmail?.setText(x?.Email)
            binding?.personLinkIn?.setText(x?.linkIn)
            binding?.personTwitter?.setText(x?.twitter)
            binding?.personeFaceBook?.setText(x?.faceBook)
            binding?.personInfo?.setText(x?.personInformation)
        }
        binding?.save?.setOnClickListener {
            if (binding?.personeName?.text.toString().isBlank()) {
                binding?.personNameText?.isErrorEnabled = true
                Toast.makeText(context, "name is empty", Toast.LENGTH_LONG).show()
            } else {
                if (id != "empty") {
                    viewModel.setOnePerson(
                        id, PersonInformation(
                            binding?.personeName?.text.toString(),
                            binding?.personeNumber?.text.toString(),
                            binding?.personEmail?.text.toString(),
                            binding?.personLinkIn?.text.toString(),
                            binding?.personTwitter?.text.toString(),
                            binding?.personeFaceBook?.text.toString(),
                            binding?.personInfo?.text.toString()
                        )
                    )
                    viewModel.getOnePerson(id)
                } else {
                    viewModel.setPersonData(
                        PersonInformation(
                            binding?.personeName?.text.toString(),
                            binding?.personeNumber?.text.toString(),
                            binding?.personEmail?.text.toString(),
                            binding?.personLinkIn?.text.toString(),
                            binding?.personTwitter?.text.toString(),
                            binding?.personeFaceBook?.text.toString(),
                            binding?.personInfo?.text.toString()
                        )
                    )
                }
                val action =
                    EditPersonInfoFragmentDirections.actionEditParsoneInfoFragmentToListOfPersonsFragment()
                findNavController().navigate(action)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}