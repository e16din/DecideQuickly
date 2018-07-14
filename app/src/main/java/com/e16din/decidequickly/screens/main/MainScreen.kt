package com.e16din.decidequickly.screens.main

import com.e16din.datamanager.get
import com.e16din.datamanager.put
import com.e16din.decidequickly.data.Criterion
import com.e16din.decidequickly.data.Decision

enum class CriteriaState { Empty, Success, Fail }

class MainScreen(private val user: User,
                 private val system: System,
                 private val app: App) {

    interface User {
        fun showMinimalCriteriaScreen(criteria: ArrayList<Criterion>)

        fun showOptimalCriteriaScreen(criteria: ArrayList<Criterion>)

        fun showResultScreen(success: Boolean)

        fun hidePlayButton()

        fun showMinimalCriteriaButton(passed: CriteriaState)

        fun showOptimalCriteriaButton(passed: CriteriaState)

        fun showProcessingProgress()

        fun hideScreen()
    }

    interface App {
        val history: ArrayList<Decision>
    }

    interface System {
        fun showAfterDelay(delayMs: Long, callback: () -> Unit)
    }

    object DATA {
        const val DECISION = "DECISION"
    }

    var decision = DATA.DECISION.get() ?: Decision()
        set(value) {
            field = value
            DATA.DECISION.put(value)
        }

    fun onCreate() {
        if (decision.playng) {
            user.hidePlayButton()
        }
        handleMinimalCriteria()
        handleOptimalCriteria()
    }

    fun onPlayClick() {
        decision.playng = true
        user.showMinimalCriteriaButton(CriteriaState.Empty)
        user.hidePlayButton()
    }

    fun onMinimalCriteriaResult(criteria: ArrayList<Criterion>) {
        decision.minimalCriteria = criteria
        handleMinimalCriteria()
    }

    fun onOptimalCriteriaResult(criteria: ArrayList<Criterion>) {
        decision.optimalCriteria = criteria
        handleOptimalCriteria()
    }

    private fun handleMinimalCriteria() {
        val minimalCriteriaPassed = decision.minimalCriteria.all { it.passed }
        val state = getCriteriaState(minimalCriteriaPassed)
        user.showMinimalCriteriaButton(state)

        if (minimalCriteriaPassed) {
            user.showOptimalCriteriaButton(CriteriaState.Empty)
        } else {
            showResultScreen(false)
        }
    }

    private fun handleOptimalCriteria() {
        val optimalCriteriaPassed = decision.optimalCriteria.all { it.passed }
        val state = getCriteriaState(optimalCriteriaPassed)
        user.showOptimalCriteriaButton(state)

        showResultScreen(optimalCriteriaPassed)
    }

    private fun showResultScreen(criteriaPassed: Boolean) {
        user.showProcessingProgress()
        system.showAfterDelay(1500) {
            user.showResultScreen(criteriaPassed)
            user.hideScreen()
        }
    }

    private fun getCriteriaState(criteriaPassed: Boolean): CriteriaState {
        return if (criteriaPassed)
            CriteriaState.Success
        else
            CriteriaState.Fail
    }

    fun onCauseChanged(cause: String) {
        decision.cause = cause
    }

    fun onMinimalCriteriaClick() {
        user.showMinimalCriteriaScreen(decision.minimalCriteria)
    }

    fun onOptimalCriteriaClick() {
        user.showOptimalCriteriaScreen(decision.optimalCriteria)
    }
}