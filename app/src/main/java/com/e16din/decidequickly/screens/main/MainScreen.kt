package com.e16din.decidequickly.screens.main

import com.e16din.decidequickly.app.IDecideQuicklyMvpApp
import com.e16din.decidequickly.data.Criterion
import com.e16din.decidequickly.data.Decision
import com.e16din.decidequickly.screens.criteria.CriteriaScreen
import com.e16din.screensadapter.annotation.model.Screen
import com.e16din.screensmodel.ScreenModel

@Screen
class MainScreen {

    interface IUser : ScreenModel.User {
        fun showDecisionsHistory(decisions: ArrayList<Decision>)
        fun showCriteriaScreen(decision: Decision, minimal: CriteriaScreen.Type)
    }

    interface ISystem : ScreenModel.System {
    }

    lateinit var user: IUser
    lateinit var system: ISystem
    lateinit var app: IDecideQuicklyMvpApp

    fun onCreate() {
        user.showDecisionsHistory(app.decisions)
    }

    fun onStartClick(decisionText: String) {
        val decision = Decision(text = decisionText)
        decision.minimalCriteria = arrayListOf(Criterion(), Criterion(), Criterion())

        app.addDecisionToHistory(decision)
        user.showCriteriaScreen(decision, CriteriaScreen.Type.Minimal)
    }
}