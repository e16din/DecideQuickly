package com.e16din.decidequickly

import android.app.Application
import com.e16din.datamanager.DataManager


class DecideQuicklyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        DataManager.initDefaultDataBox(this)
        DataManager.getBox().useCommit = false
    }

}