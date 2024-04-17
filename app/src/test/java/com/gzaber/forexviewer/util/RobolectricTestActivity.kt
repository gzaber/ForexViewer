package com.gzaber.forexviewer.util

import android.app.Application
import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.test.core.app.ApplicationProvider
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.robolectric.Shadows

class RobolectricTestActivity: TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        val appContext: Application = ApplicationProvider.getApplicationContext()
        val activityInfo = ActivityInfo().apply {
            name = ComponentActivity::class.java.name
            packageName = appContext.packageName
        }
        Shadows.shadowOf(appContext.packageManager).addOrUpdateActivity(activityInfo)
    }
}