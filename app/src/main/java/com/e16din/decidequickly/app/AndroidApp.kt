package com.e16din.decidequickly.app

import android.app.Application
import com.e16din.datamanager.DataManager
import com.e16din.datamanager.getData
import com.e16din.datamanager.putData
import com.e16din.decidequickly.data.Decision
import com.e16din.decidequickly.screens.main.MainBinder
import com.e16din.decidequickly.server.RequestManager
import com.e16din.screensadapter.GeneratedScreensAdapter
import com.e16din.screensadapter.ScreensAdapter
import com.e16din.screensadapter.ScreensAdapterApplication

class AndroidApp : Application(),
        ScreensAdapterApplication,
        IDecideQuicklyMvpApp {

    object DATA {
        const val KEY_DECISIONS = "KEY_DECISIONS"
    }

    override val screensAdapter: ScreensAdapter<*, *> by lazy {
        val app = this
        val server = RequestManager
        val delayForSplashMs = 1000L
        GeneratedScreensAdapter(this, app, server, delayForSplashMs)
    }

    override var decisions: ArrayList<Decision> = ArrayList()

    override fun onCreate() {
        super.onCreate()

        DataManager.initDefaultDataBox(this)
        DataManager.getBox()?.useCommit = false

        decisions = DATA.KEY_DECISIONS.getData<ArrayList<Decision>>() ?: ArrayList()

        val screenSettings = MainBinder.createScreenSettings()
        screensAdapter.setFirstScreen(screenSettings)

        screensAdapter.start()
    }

    override fun onHideAllScreens(screensCount: Int) {
    }

    override fun onStart(launchNumber: Int) {

    }

    override fun addDecisionToHistory(decision: Decision) {
        decisions.add(decision)
    }

    override fun saveDecision(decision: Decision) {
        //todo: add to DB
        DATA.KEY_DECISIONS.putData(decisions)
    }

    override fun removeDecision(decision: Decision) {
        // todo: remove from DB
    }

}