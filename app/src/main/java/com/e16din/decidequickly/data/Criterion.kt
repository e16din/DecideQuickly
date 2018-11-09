package com.e16din.decidequickly.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Criterion(var text: String = "",
                     var checked: Boolean = false) : Parcelable
