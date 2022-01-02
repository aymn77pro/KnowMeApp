package com.aymn.knowmeapp.persons.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aymn.knowmeapp.network.model.PersonInformation
import com.example.knowmeapp.R
import com.example.knowmeapp.databinding.PersonItem1Binding

class PesrsonListAdabter(private val context: Context):
ListAdapter<PersonInformation,PesrsonListAdabter.PersonViewHolder>(DiffCallBack){


    class PersonViewHolder(private val binding:PersonItem1Binding):RecyclerView.ViewHolder(binding.root){
        val card:CardView = itemView.findViewById(R.id.cerdView)
        fun bind(personInformation: PersonInformation){
            binding.name.text = personInformation.Name

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonViewHolder {
        return PersonViewHolder(
           PersonItem1Binding.inflate(LayoutInflater.from(parent.context))
        )

    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val current = getItem(position)
        holder.card.setOnClickListener {
            val action = ListOfPersonsFragmentDirections.actionListOfPersonsFragmentToParsoneInfoFragment(current.Name)
            it.findNavController().navigate(action)
        }
        holder.bind(current)
        holder.card.setOnClickListener {
            val action = ListOfPersonsFragmentDirections.actionListOfPersonsFragmentToParsoneInfoFragment(current.Name)
            holder.itemView.findNavController().navigate(action)
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<PersonInformation>() {
            override fun areItemsTheSame(oldItem: PersonInformation, newItem: PersonInformation): Boolean {
                return oldItem.Name == newItem.Name
            }

            override fun areContentsTheSame(oldItem: PersonInformation, newItem: PersonInformation): Boolean {
                return oldItem.Name == newItem.Name
            }
        }
    }

}

