package com.aymn.knowmeapp.persons.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aymn.knowmeapp.network.model.PersonInformation
import com.bumptech.glide.Glide
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.PersonItem1Binding

class PesrsonListAdabter(private val context: Context) :
    ListAdapter<PersonInformation, PesrsonListAdabter.PersonViewHolder>(DiffCallBack) {


    class PersonViewHolder(var binding: PersonItem1Binding) :
        RecyclerView.ViewHolder(binding.root) {




    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonViewHolder {
        return PersonViewHolder(
            PersonItem1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val current = getItem(position)

        holder.binding.name.text = current.name


         holder.binding
            .cerdView.setOnClickListener {
            val action =
                ListOfPersonsFragmentDirections.actionListOfPersonsFragmentToParsoneInfoFragment(
                    current.id,current.name,current.lattLoac.toString(),current.longLoca.toString())
            Log.d("TAG", "current id = ${current.id}")
            holder.itemView.findNavController().navigate(action)
        }

        //show person Image
       Glide.with(context).load(current.imageUri.toUri()).circleCrop().placeholder(R.drawable.loading_animation)
           .error(R.drawable.ic_baseline_account_circle_24).into(holder.binding.presonImage)
        }
// to camper between
    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<PersonInformation>() {
            override fun areItemsTheSame(
                oldItem: PersonInformation,
                newItem: PersonInformation
            ): Boolean {
                return oldItem.name === newItem.name
            }

            override fun areContentsTheSame(
                oldItem: PersonInformation,
                newItem: PersonInformation
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}