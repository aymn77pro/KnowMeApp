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
    ): PesrsonListAdabter.PersonViewHolder {
        return PesrsonListAdabter.PersonViewHolder(
            PersonItem1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PesrsonListAdabter.PersonViewHolder, position: Int) {
        val current = getItem(position)

        holder.binding.name.text = current.Name

//        Glide.with(context).load(current.imageUri.toUri())
//            .placeholder(R.drawable.loading_animation).error(R.drawable.ic_baseline_account_circle_24)
//            .into(holder.binding.img)

         holder.binding
            .root.setOnClickListener {
            val action =
                ListOfPersonsFragmentDirections.actionListOfPersonsFragmentToParsoneInfoFragment(
                    current.id,current.Name,current.lattLoac.toString(),current.longLoca.toString())
            Log.d("TAG", "current id = ${current.id}")
            holder.itemView.findNavController().navigate(action)
        }
        Log.d("TAG", "onBindViewHolder:${current.Name}+${current.lattLoac}+${current.longLoca}")


       Glide.with(context).load(current.imageUri.toUri()).circleCrop().placeholder(R.drawable.loading_animation)
           .error(R.drawable.ic_baseline_account_circle_24).into(holder.binding.presonImage)
        }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<PersonInformation>() {
            override fun areItemsTheSame(
                oldItem: PersonInformation,
                newItem: PersonInformation
            ): Boolean {
                return oldItem.Name === newItem.Name
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