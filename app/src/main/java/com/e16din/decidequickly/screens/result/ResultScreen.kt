package com.e16din.decidequickly.screens.result

import com.e16din.decidequickly.app.IDecideQuicklyMvpApp
import com.e16din.decidequickly.data.Decision
import com.e16din.screensadapter.annotation.model.Screen
import com.e16din.screensmodel.ScreenModel

@Screen
class ResultScreen {

    interface IUser : ScreenModel.User {
        fun showDecisionText(text: String)

        fun showDecisionAcceptedText()
        fun showDecisionAcceptedIcon()

        fun showDecisionRejectedText()
        fun showDecisionRejectedIcon()

        fun showMainScreen()
    }

    interface ISystem : ScreenModel.System {
    }

    lateinit var user: IUser
    lateinit var system: ISystem
    lateinit var app: IDecideQuicklyMvpApp

    private lateinit var decision: Decision

    fun onCreate(decision: Decision) {
        this.decision = decision
        user.showDecisionText(decision.text)

        val allOptimalCriteriaChecked = decision.optimalCriteria.all { it.checked }
        if (allOptimalCriteriaChecked) {
            user.showDecisionAcceptedText()
            user.showDecisionAcceptedIcon()

        } else {
            user.showDecisionRejectedText()
            user.showDecisionRejectedIcon()
        }
    }

    fun onOkClick() {
        app.saveDecision(decision)
        user.showMainScreen()
    }

    fun onBackClick() {
        onOkClick()
    }
}