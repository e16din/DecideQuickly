package com.e16din.decidequickly.data

data class Decision(var cause: String = "",
                    var playng: Boolean = false,
                    var minimalCriteria: ArrayList<Criterion> = ArrayList(),
                    var optimalCriteria: ArrayList<Criterion> = ArrayList())