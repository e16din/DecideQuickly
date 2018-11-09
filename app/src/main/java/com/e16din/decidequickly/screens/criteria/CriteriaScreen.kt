package com.e16din.decidequickly.screens.criteria

import com.e16din.decidequickly.app.IDecideQuicklyMvpApp
import com.e16din.decidequickly.data.Criterion
import com.e16din.decidequickly.data.Decision
import com.e16din.screensadapter.annotation.model.Screen
import com.e16din.screensmodel.ScreenModel

@Screen
class CriteriaScreen {

    enum class Type { Minimal, Optimal }

    interface IUser : ScreenModel.User {
        fun showCriteria(criteria: ArrayList<Criterion>)

        fun showOptimalCriteriaScreen(decision: Decision, screenType: Type)
        fun showResultScreen(decision: Decision)

        fun showMinimalCriteriaTitle()
        fun showOptimalCriteriaTitle()
    }

    interface ISystem : ScreenModel.System {
    }

    lateinit var user: IUser
    lateinit var system: ISystem
    lateinit var app: IDecideQuicklyMvpApp

    private lateinit var type: Type
    private lateinit var decision: Decision


    fun onCreate(decision: Decision, type: Type) {
        this.decision = decision
        this.type = type

        when (type) {
            Type.Minimal -> {
                user.showMinimalCriteriaTitle()
                user.showCriteria(decision.minimalCriteria)
            }
            Type.Optimal -> {
                user.showOptimalCriteriaTitle()
                user.showCriteria(decision.optimalCriteria)
            }
        }
    }

    fun onNextClick() {
        app.saveDecision(decision)
        when (type) {
            Type.Minimal -> {
                if (decision.minimalCriteria.all { true }) {
                    decision.optimalCriteria = arrayListOf(Criterion(), Criterion(), Criterion())
                    app.saveDecision(decision)

                    user.showOptimalCriteriaScreen(decision, CriteriaScreen.Type.Optimal)
                } else {
                    user.showResultScreen(decision)
                }
            }
            Type.Optimal -> user.showResultScreen(decision)
        }
    }

    fun onBackClick() {
        user.hideScreen()
    }
}