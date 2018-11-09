package com.e16din.decidequickly.screens.main

import android.support.v7.widget.LinearLayoutManager
import com.e16din.decidequickly.R
import com.e16din.decidequickly.app.IDecideQuicklyMvpApp
import com.e16din.decidequickly.data.Decision
import com.e16din.decidequickly.screens.criteria.CriteriaBinder
import com.e16din.decidequickly.screens.criteria.CriteriaScreen
import com.e16din.decidequickly.utils.string
import com.e16din.screensadapter.ScreensAdapter
import com.e16din.screensadapter.annotation.BindScreen
import com.e16din.screensadapter.binders.ScreenBinder
import com.e16din.screensadapter.settings.ScreenSettings
import kotlinx.android.synthetic.main.screen_main.view.*


@BindScreen(screen = MainScreen::class)
class MainBinder(adapter: ScreensAdapter<*, *>) : ScreenBinder<MainScreen>(adapter),
        MainScreen.IUser,
        MainScreen.ISystem {
    companion object {
        fun createScreenSettings() = ScreenSettings(
                MainScreen::class.java,
                R.layout.screen_main,
                themeId = R.style.AppTheme
        )
    }

    override fun onBind() {
        screen.user = this
        screen.system = this
        screen.app = adapter.getApp() as IDecideQuicklyMvpApp

        screen.onCreate()

        view.vStartButton.setOnClickListener {
            screen.onStartClick(view.vDecisionField.string())
        }
    }

    override fun showDecisionsHistory(decisions: ArrayList<Decision>) {
        view.vHistoryList.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view.vHistoryList.adapter = DecisionsAdapter(decisions)
    }

    override fun showCriteriaScreen(decision: Decision, screenType: CriteriaScreen.Type) {
        val settings = CriteriaBinder.createScreenSettings(decision, screenType)
        adapter.showNextScreen(settings)
    }
}
