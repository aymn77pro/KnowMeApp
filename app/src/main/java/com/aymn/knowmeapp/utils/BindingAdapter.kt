package com.aymn.knowmeapp.utils

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.knowmeapp.R

@BindingAdapter("imag")
fun bindImage(imageView: ImageView, imagUrl: String?){
    imagUrl.let {
        val imagUri = imagUrl?.toUri()?.buildUpon()?.build()
        Log.d("TAG", "bindImage: $imagUrl ")

        Glide.with(imageView.context)
            .load(imagUri)
            .circleCrop()
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(imageView)
    }
}