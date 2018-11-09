package com.e16din.decidequickly.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Decision(var id: Int = -1,
                    var text: String = "",
                    var minimalCriteria: ArrayList<Criterion> = ArrayList(),
                    var optimalCriteria: ArrayList<Criterion> = ArrayList(),
                    var state: Decision.State = State.New) : Parcelable {

    enum class State { New, Accepted, Rejected }
}