package com.aymn.knowmeapp.persons.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aymn.knowmeapp.ViewModelFactory
import com.aymn.knowmeapp.network.model.PersonInformation
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.PersonDetailsLayoutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class PersonInfoFragment : Fragment() {
    private val viewModel: PersonViewModel by activityViewModels {
        ViewModelFactory()
    }
    private val navigationArgs: PersonInfoFragmentArgs by navArgs()


    private var _binding: PersonDetailsLayoutBinding? = null
    val binding get() = _binding

    private val REQUEST_CODE = 100
    private var fileImage: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PersonDetailsLayoutBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id

        viewModel.getOnePerson(id)



        if (id != "empty") {
            binding?.delete?.setOnClickListener {
                showDeleteDialog()
            }
            viewModel.personData.observe(viewLifecycleOwner, { personData ->
                binding?.person = personData
                Log.e("TAG", "onViewCreated:${personData.imported} ")
                binding?.executePendingBindings()

                //region location
                binding?.locationCard?.setOnClickListener {
                    if (personData.lattLoac.isNullOrBlank() && personData.longLoca.isNullOrBlank()) {
                        Toast.makeText(
                            context,
                            "go to map and choose your friend location :-)",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Log.d("TAG", "location: ${navigationArgs.latt}+${navigationArgs.long}  ")
                        val gmmIntentUri =
                            Uri.parse("geo:0,0?q=${personData.lattLoac},${personData.longLoca}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        startActivity(mapIntent)
                    }
                }
                binding?.locationCard?.setOnLongClickListener {
                    val selectLocation =
                        PersonInfoFragmentDirections.actionParsoneInfoFragmentToMapsFragment(
                            id = id,
                            name = personData.Name
                        )
                    findNavController().navigate(selectLocation)
                    true
                }
                //endregion

                //region imported
                if (personData.imported == true) {
                    binding?.importedImage?.frame = 79
                } else {
                    binding?.importedImage?.frame = 1
                }
                binding?.iportedCard?.setOnClickListener {
                    if (personData.imported == true) {
                        personData.imported = false
                        binding?.importedImage?.frame = 1
                    } else {
                        personData.imported = true
                        binding?.importedImage?.playAnimation()
                    }
                }
                //endregion


            })
        } else {
            //region location
            binding?.locationCard?.setOnClickListener {
                if (navigationArgs.latt.isNullOrBlank() && navigationArgs.long.isNullOrBlank()) {
                    Toast.makeText(
                        context,
                        "go to map and choose your friend location :-)",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Log.d("TAG", "location: ${navigationArgs.latt}+${navigationArgs.long}  ")
                    val gmmIntentUri =
                        Uri.parse("geo:0,0?q=${navigationArgs.latt},${navigationArgs.long}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                }
            }
            binding?.locationCard?.setOnLongClickListener {
                val selectLocation =
                    PersonInfoFragmentDirections.actionParsoneInfoFragmentToMapsFragment(
                        id = id,
                        name = "Add location"
                    )
                findNavController().navigate(selectLocation)
                true
            }
            //endregion

            //region imported
            binding?.iportedCard?.setOnClickListener {
                viewModel.personData.observe(viewLifecycleOwner, { personData ->
                    if (personData.imported == false) {
                        personData.imported = true
                        binding?.importedImage?.playAnimation()
                    } else {
                        personData.imported = false
                        binding?.importedImage?.frame = 1
                    }
                })

            }
            //endregion
            binding?.delete?.isVisible = false
        }



        binding?.userImage?.setOnClickListener {
            openGalleryForImage()
        }


        //region update person data
        binding?.edit?.setOnClickListener {
            viewModel.personData.observe(viewLifecycleOwner, { personInformation ->
                if (binding?.Name?.text.toString().isBlank()) {
                    binding?.personNameText?.isErrorEnabled = true
                    Toast.makeText(context, "name is empty", Toast.LENGTH_LONG).show()
                } else {
                    if (id != "empty") {
                        viewModel.setOnePerson(
                            id, PersonInformation(
                                Name = binding?.Name?.text.toString(),
                                Number = binding?.userNumber?.text.toString(),
                                Email = binding?.email?.text.toString(),
                                linkIn = binding?.linkIn?.text.toString(),
                                twitter = binding?.twitter?.text.toString(),
                                faceBook = binding?.faceBook?.text.toString(),
                                personInformation = binding?.persominfo?.text.toString(),
                                imageUri = personInformation.imageUri,
                                lattLoac = navigationArgs.latt,
                                longLoca = navigationArgs.long,
                                imported = personInformation.imported
                            ), fileImage
                        )
                        Log.d(
                            "TAG",
                            "edit  person location: ${viewModel.personData.value?.longLoca} "
                        )
                        viewModel.getOnePerson(id)
                    } else {
                        viewModel.setPersonData(
                            PersonInformation(
                                binding?.Name?.text.toString(),
                                binding?.userNumber?.text.toString(),
                                binding?.email?.text.toString(),
                                binding?.linkIn?.text.toString(),
                                binding?.twitter?.text.toString(),
                                binding?.faceBook?.text.toString(),
                                binding?.persominfo?.text.toString(),
                                imageUri = fileImage.toString(),
                                lattLoac = navigationArgs.latt,
                                longLoca = navigationArgs.long,
                                imported = personInformation.imported
                            ), fileImage
                        )
                        Log.d(
                            "TAG",
                            "add new person location: ${viewModel.personData.value?.longLoca} "
                        )
                    }
                    val action =
                        PersonInfoFragmentDirections.actionParsoneInfoFragmentToListOfPersonsFragment()
                    findNavController().navigate(action)
                }
            })
            //endregion

            binding?.delete?.setOnClickListener {
                showDeleteDialog()
            }
//region location
            binding?.locationCard?.setOnLongClickListener {
                val actionMap =
                    PersonInfoFragmentDirections.actionParsoneInfoFragmentToMapsFragment(
                        id,
                        navigationArgs.name
                    )
                findNavController().navigate(actionMap)
                true
            }
            //endregion


            //region card action for imported
            viewModel.personData.observe(viewLifecycleOwner, { personInfo ->
                binding?.iportedCard?.setOnClickListener {
                    Log.d("TAG", "imported:${personInfo.imported}")
                }
            })
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
            binding?.userImage?.setImageURI(data?.data) // handle chosen image
            fileImage = data?.data!!
        }
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext(),R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question, navigationArgs.name))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.Cancel)) { _, _ -> }
            .setPositiveButton(R.string.Discard) { _, _ ->
                viewModel.deletePerson(navigationArgs.id)
                Toast.makeText(context, "${navigationArgs.name} has been deleted ", Toast.LENGTH_SHORT).show()
                findNavController().navigate(PersonInfoFragmentDirections.actionParsoneInfoFragmentToListOfPersonsFragment())
            }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}