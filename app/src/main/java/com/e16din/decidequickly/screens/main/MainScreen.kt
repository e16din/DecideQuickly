package com.e16din.decidequickly.screens.main

import com.e16din.decidequickly.data.Criterion
import com.e16din.decidequickly.data.Decision

enum class CriteriaState { Empty, Success, Fail }

typealias AfterDelayCallback = () -> Unit

open class MainScreen(private val user: User,
                      private val system: System,
                      private val app: App) {

    companion object {
        const val DELAY_BEFORE_SHOW_RESULT = 1500L
    }

    interface User {
        fun showMinimalCriteriaScreen(criteria: ArrayList<Criterion>)

        fun showOptimalCriteriaScreen(criteria: ArrayList<Criterion>)

        fun showResultScreen(success: Boolean)

        fun hidePlayButton()

        fun showMinimalCriteriaButton(passed: CriteriaState)

        fun showOptimalCriteriaButton(passed: CriteriaState)

        fun showProcessingProgress()

        fun clearScreen()
    }

    interface App {
        fun addDecisionToHistory(decision: Decision)
    }

    interface System {
        fun doAfterDelay(delayMs: Long, callback: AfterDelayCallback)

        fun saveDecision(decision: Decision)
    }

    lateinit var decision: Decision

    fun onCreate(decision: Decision) {
        this.decision = decision

        if (decision.playing) {
            user.hidePlayButton()
        }

        if (decision.minimalCriteria.isNotEmpty()) {
            handleMinimalCriteria()
        } else if (decision.playing) {
            user.showMinimalCriteriaButton(CriteriaState.Empty)
        }

        if (decision.optimalCriteria.isNotEmpty()) {
            handleOptimalCriteria()
        } else {
            val minimalCriteriaPassed = getCriteriaState(decision.minimalCriteria) == CriteriaState.Success
            if (decision.playing && minimalCriteriaPassed) {
                user.showOptimalCriteriaButton(CriteriaState.Empty)
            }
        }
    }

    fun onMenuActionDoneClick() {
        app.addDecisionToHistory(decision)
        user.clearScreen()
    }

    fun onPlayClick() {
        decision.playing = true
        system.saveDecision(decision)

        user.showMinimalCriteriaButton(CriteriaState.Empty)
        user.hidePlayButton()
    }

    fun onMinimalCriteriaResult(criteria: ArrayList<Criterion>) {
        decision.minimalCriteria = criteria
        system.saveDecision(decision)

        handleMinimalCriteria()
    }

    fun onOptimalCriteriaResult(criteria: ArrayList<Criterion>) {
        decision.optimalCriteria = criteria
        system.saveDecision(decision)

        handleOptimalCriteria()
    }

    private fun handleMinimalCriteria() {
        val state = getCriteriaState(decision.minimalCriteria)
        user.showMinimalCriteriaButton(state)

        if (state == CriteriaState.Success) {
            user.showOptimalCriteriaButton(CriteriaState.Empty)
        }

        if (state == CriteriaState.Fail) {
            showResultScreen(false)
        }
    }

    private fun handleOptimalCriteria() {
        val state = getCriteriaState(decision.optimalCriteria)
        user.showOptimalCriteriaButton(state)
        showResultScreen(state == CriteriaState.Success)
    }

    private fun showResultScreen(criteriaPassed: Boolean) {
        user.showProcessingProgress()
        system.doAfterDelay(DELAY_BEFORE_SHOW_RESULT) {
            user.showResultScreen(criteriaPassed)
        }

    }

    private fun getCriteriaState(criteria: ArrayList<Criterion>): CriteriaState {
        val criteriaPassed =
                criteria.isNotEmpty() && criteria.all { it.passed }

        return if (criteriaPassed)
            CriteriaState.Success
        else
            CriteriaState.Fail
    }

    fun onCauseChanged(cause: String) {
        decision.cause = cause
        system.saveDecision(decision)
    }

    fun onMinimalCriteriaClick() {
        user.showMinimalCriteriaScreen(decision.minimalCriteria)
    }

    fun onOptimalCriteriaClick() {
        user.showOptimalCriteriaScreen(decision.optimalCriteria)
    }
}