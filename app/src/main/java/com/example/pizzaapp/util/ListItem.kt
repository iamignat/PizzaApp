package com.example.pizzaapp.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListItem(
    val title: String,
    val imageName: String,
    val ingredients : String,
    val price : String,
    val link : String,
) : Parcelable