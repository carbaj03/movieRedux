package com.acv.movieredux.ui.preferences

import android.content.Context
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

fun settings(context: Context): Settings =
    AndroidSettings(context.getSharedPreferences("settings", Context.MODE_PRIVATE))

class AppUserDefaults(private val settings: Settings) {
    private val REGION_KEY = "region"
    private val ALWAYS_ORIGINAL_TITLE_KEY = "alwaysOriginalTitle"

    var region: String
        get() = settings.getString(REGION_KEY, "US")
        set(value) {
            settings[REGION_KEY] = value
        }

    var alwaysOriginalTitle: Boolean
        get() = settings.getBoolean(ALWAYS_ORIGINAL_TITLE_KEY, false)
        set(value) {
            settings[ALWAYS_ORIGINAL_TITLE_KEY] = value
        }
}
