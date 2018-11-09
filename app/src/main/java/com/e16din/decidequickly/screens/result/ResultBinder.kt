package com.e16din.decidequickly.screens.result

import android.os.Bundle
import com.e16din.decidequickly.R
import com.e16din.decidequickly.app.IDecideQuicklyMvpApp
import com.e16din.decidequickly.data.Decision
import com.e16din.decidequickly.screens.main.MainBinder
import com.e16din.screensadapter.ScreensAdapter
import com.e16din.screensadapter.annotation.BindScreen
import com.e16din.screensadapter.binders.ScreenBinder
import com.e16din.screensadapter.settings.ScreenSettings
import kotlinx.android.synthetic.main.screen_result.view.*


@BindScreen(screen = ResultScreen::class)
class ResultBinder(adapter: ScreensAdapter<*, *>) : ScreenBinder<ResultScreen>(adapter),
        ResultScreen.IUser,
        ResultScreen.ISystem {

    companion object {
        const val KEY_DECISION = "KEY_DECISION"

        fun createScreenSettings(decision: Decision): ScreenSettings {
            val data = Bundle()
            data.putParcelable(KEY_DECISION, decision)

            return ScreenSettings(
                    ResultScreen::class.java,
                    R.layout.screen_result,
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
        screen.onCreate(decision)

        view.vOkButton.setOnClickListener {
            screen.onOkClick()
        }
    }

    override fun showDecisionText(text: String) {
        view.vResultDecisionTextLabel.text = text
    }

    override fun showDecisionAcceptedText() {
        view.vDecisionResultLabel.text = getString(R.string.screen_result_decision_accept)
    }

    override fun showDecisionAcceptedIcon() {
        view.vDecisionResultImage.setImageResource(R.drawable.ic_check_circle_black_24dp)
    }

    override fun showDecisionRejectedText() {
        view.vDecisionResultLabel.text = getString(R.string.screen_result_decision_reject)
    }

    override fun showDecisionRejectedIcon() {
        view.vDecisionResultImage.setImageResource(R.drawable.ic_cancel_black_24dp)
    }

    override fun showMainScreen() {
        val settings = MainBinder.createScreenSettings()
        adapter.showNextScreen(settings)
    }
}
