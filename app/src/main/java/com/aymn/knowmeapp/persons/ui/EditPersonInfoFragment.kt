package com.aymn.knowmeapp.persons.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aymn.knowmeapp.ViewModelFactory
import com.aymn.knowmeapp.network.model.PersonInformation
import com.bumptech.glide.Glide
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.FragmentEditPersonInfoBinding

class EditPersonInfoFragment : Fragment() {

    private val viewModel: PersonViewModel by activityViewModels {
        ViewModelFactory()
    }
    private val REQUEST_CODE = 100
    private var fileImage: Uri?=null

    private val navigationArgs: EditPersonInfoFragmentArgs by navArgs()

    private var _binding: FragmentEditPersonInfoBinding? = null
    val binding get() = _binding



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
        fileImage = null
        binding?.personImage?.setOnClickListener { openGalleryForImage() }
        if (id != "empty") {
            val x = viewModel.personData.value
            binding?.personeName?.setText(x?.Name)
            binding?.personeNumber?.setText(x?.Number)
            binding?.personEmail?.setText(x?.Email)
            binding?.personLinkIn?.setText(x?.linkIn)
            binding?.personTwitter?.setText(x?.twitter)
            binding?.personeFaceBook?.setText(x?.faceBook)
            binding?.personInfo?.setText(x?.personInformation)
            Glide.with(requireContext()).load(viewModel.personData.value?.imageUri?.toUri()).circleCrop()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_baseline_account_circle_24)
                .into(binding?.personImage!!)

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
                            binding?.personInfo?.text.toString(),
                            imageUri = viewModel.personData.value?.imageUri!!,
                            lattLoac = viewModel.personData.value?.lattLoac,
                            longLoca = viewModel.personData.value?.longLoca
                        ),fileImage)
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
                            binding?.personInfo?.text.toString(),
                            imageUri = fileImage.toString(),
                            lattLoac = viewModel.personData.value?.lattLoac,
                            longLoca = viewModel.personData.value?.longLoca
                        ),fileImage
                    )
                }
                val action =
                    EditPersonInfoFragmentDirections.actionEditParsoneInfoFragmentToListOfPersonsFragment()
                findNavController().navigate(action)
            }
        }
        binding?.map?.setOnClickListener {
            val actionMap = EditPersonInfoFragmentDirections.actionEditParsoneInfoFragmentToMapsFragment(id)
            findNavController().navigate(actionMap)
        }
    }
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
              binding?.personImage?.setImageURI(data?.data) // handle chosen image
            fileImage = data?.data!!
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}