package com.mitocode.mitocine.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class  Movie(

        var id: Int,
        var title: String,
        var image: String,
        var description: String
): Parcelable
