package com.e16din.decidequickly.app

import com.e16din.decidequickly.data.Decision
import com.e16din.screensadapter.annotation.model.App
import com.e16din.screensmodel.AppModel

@App
interface IDecideQuicklyMvpApp : AppModel {
    fun addDecisionToHistory(decision: Decision)
    fun saveDecision(decision: Decision)
    fun removeDecision(decision: Decision)

    val decisions: ArrayList<Decision>
}
