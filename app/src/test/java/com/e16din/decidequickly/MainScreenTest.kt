package com.e16din.decidequickly

import com.e16din.decidequickly.data.Criterion
import com.e16din.decidequickly.data.Decision
import com.e16din.decidequickly.screens.main.CriteriaState
import com.e16din.decidequickly.screens.main.MainScreen
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.junit.MockitoJUnitRunner


/**
 * Тестируем главный экран
 */
@RunWith(MockitoJUnitRunner::class)
class MainScreenTest {

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T

    lateinit var app: MainScreen.App
    lateinit var user: MainScreen.User
    lateinit var system: MainScreen.System

    @Before
    fun setUp() {
        app = Mockito.mock(MainScreen.App::class.java)
        user = Mockito.mock(MainScreen.User::class.java)
        system = Mockito.mock(MainScreen.System::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun createScreen() {
        val mainScreen = MainScreen(user, system, app)
        mainScreen.onCreate(Decision())

        Mockito.verify(user, never()).hidePlayButton()

        Mockito.verify(user, never()).showMinimalCriteriaButton(CriteriaState.Empty)
        Mockito.verify(user, never()).showMinimalCriteriaButton(CriteriaState.Fail)
        Mockito.verify(user, never()).showMinimalCriteriaButton(CriteriaState.Success)

        Mockito.verify(user, never()).showOptimalCriteriaButton(CriteriaState.Empty)
        Mockito.verify(user, never()).showOptimalCriteriaButton(CriteriaState.Fail)
        Mockito.verify(user, never()).showOptimalCriteriaButton(CriteriaState.Success)
    }

    @Test
    @Throws(Exception::class)
    fun restoreScreenWithMinimalCriteriaStateEmpty() {
        val decision = Decision(playing = true)
        val mainScreen = MainScreen(user, system, app)
        mainScreen.onCreate(decision)

        Mockito.verify(user).hidePlayButton()
        Mockito.verify(user).showMinimalCriteriaButton(CriteriaState.Empty)

        Mockito.verify(user, never()).showMinimalCriteriaButton(CriteriaState.Fail)
        Mockito.verify(user, never()).showMinimalCriteriaButton(CriteriaState.Success)

        Mockito.verify(user, never()).showOptimalCriteriaButton(CriteriaState.Empty)
        Mockito.verify(user, never()).showOptimalCriteriaButton(CriteriaState.Fail)
        Mockito.verify(user, never()).showOptimalCriteriaButton(CriteriaState.Success)
    }

    @Test
    @Throws(Exception::class)
    fun restoreScreenWithMinimalCriteriaStateFail() {
        val criteria = ArrayList<Criterion>()
        criteria.add(Criterion(passed = false))
        criteria.add(Criterion(passed = false))
        criteria.add(Criterion(passed = true))

        val decision = Decision(playing = true, minimalCriteria = criteria)
        val mainScreen = MainScreen(user, system, app)
        mainScreen.onCreate(decision)

        Mockito.verify(user).hidePlayButton()
        Mockito.verify(user).showMinimalCriteriaButton(CriteriaState.Fail)
        Mockito.verify(user).showProcessingProgress()

        //todo: check showResultScreen(false)
//        Mockito.`when`(system.doAfterDelay(
//                ArgumentMatchers.anyLong(),
//                any())).thenAnswer(Answer<Any> { invocation ->
//            (invocation.arguments[1] as AfterDelayCallback).invoke()
//            null
//        })
//        system.doAfterDelay(MainScreen.DELAY_BEFORE_SHOW_RESULT, {
//            user.showResultScreen(false)
//        })
//        Mockito.verify(user).showResultScreen(false)

        Mockito.verify(user, never()).showMinimalCriteriaButton(CriteriaState.Empty)
        Mockito.verify(user, never()).showMinimalCriteriaButton(CriteriaState.Success)

        Mockito.verify(user, never()).showOptimalCriteriaButton(CriteriaState.Empty)
        Mockito.verify(user, never()).showOptimalCriteriaButton(CriteriaState.Fail)
        Mockito.verify(user, never()).showOptimalCriteriaButton(CriteriaState.Success)

    }

    @Test
    @Throws(Exception::class)
    fun restoreScreenWithMinimalCriteriaStateSuccess() {
        val criteria = ArrayList<Criterion>()
        criteria.add(Criterion(passed = true))
        criteria.add(Criterion(passed = true))
        criteria.add(Criterion(passed = true))

        val decision = Decision(playing = true, minimalCriteria = criteria)
        val mainScreen = MainScreen(user, system, app)
        mainScreen.onCreate(decision)

        Mockito.verify(user).hidePlayButton()
        Mockito.verify(user).showMinimalCriteriaButton(CriteriaState.Success)
    }

    @Test
    @Throws(Exception::class)
    fun clickDoneDecision() {
        val mainScreen = MainScreen(user, system, app)
        mainScreen.onCreate(Decision())
        mainScreen.onMenuActionDoneClick()

        Mockito.verify(app).addDecisionToHistory(mainScreen.decision)
        Mockito.verify(user).clearScreen()
    }
}
