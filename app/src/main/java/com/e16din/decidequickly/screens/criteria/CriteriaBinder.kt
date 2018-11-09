package com.e16din.decidequickly.screens.criteria

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.e16din.decidequickly.R
import com.e16din.decidequickly.app.IDecideQuicklyMvpApp
import com.e16din.decidequickly.data.Criterion
import com.e16din.decidequickly.data.Decision
import com.e16din.decidequickly.screens.result.ResultBinder
import com.e16din.screensadapter.ScreensAdapter
import com.e16din.screensadapter.annotation.BindScreen
import com.e16din.screensadapter.binders.ScreenBinder
import com.e16din.screensadapter.settings.ScreenSettings
import kotlinx.android.synthetic.main.screen_criteria.view.*


@BindScreen(screen = CriteriaScreen::class)
class CriteriaBinder(adapter: ScreensAdapter<*, *>) : ScreenBinder<CriteriaScreen>(adapter),
        CriteriaScreen.IUser,
        CriteriaScreen.ISystem {

    companion object {
        const val KEY_DECISION = "KEY_DECISION"
        const val KEY_SCREEN_TYPE = "KEY_SCREEN_TYPE"

        fun createScreenSettings(decision: Decision, screenType: CriteriaScreen.Type): ScreenSettings {
            val data = Bundle()
            data.putParcelable(KEY_DECISION, decision)
            data.putSerializable(KEY_SCREEN_TYPE, screenType)

            return ScreenSettings(
                    CriteriaScreen::class.java,
                    R.layout.screen_criteria,
                    themeId = R.style.AppTheme,
                    data = data
            )
        }
    }

    override fun onBind() {
        screen.user = this
        screen.system = this
        screen.app = adapter.getApp() as IDecideQuicklyMvpApp

        val decision = data?.getParcelable<Decision>(KEY_DECISION)!!
        val screenType = data?.getSerializable(KEY_SCREEN_TYPE) as CriteriaScreen.Type
        screen.onCreate(decision, screenType)

        adapter.setOnBackPressedListener {
            screen.onBackClick()
        }

        view.vNextButton.setOnClickListener {
            screen.onNextClick()
        }
    }

    override fun showCriteria(criteria: ArrayList<Criterion>) {
        view.vCriteriaList.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view.vCriteriaList.adapter = CriteriaAdapter(criteria)
    }

    override fun showOptimalCriteriaScreen(decision: Decision, screenType: CriteriaScreen.Type) {
        val settings = CriteriaBinder.createScreenSettings(decision, screenType)
        adapter.showNextScreen(settings)
    }

    override fun showResultScreen(decision: Decision) {
        val settings = ResultBinder.createScreenSettings(decision)
        adapter.showNextScreen(settings)
    }

    override fun showMinimalCriteriaTitle() {
        activity.title = getString(R.string.criteria_minimal_title)
    }

    override fun showOptimalCriteriaTitle() {
        activity.title = getString(R.string.criteria_optimal_title)
    }
}
