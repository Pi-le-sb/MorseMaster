package com.morsemaster.app.data

import android.content.Context
import androidx.core.content.edit

object UserSettings {
    private const val PREFS = "user_settings"

    fun isHapticsEnabled(ctx: Context) =
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean("haptics", true)

    fun isAudioEnabled(ctx: Context) =
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean("audio", true)

    fun setHaptics(ctx: Context, on: Boolean) =
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit { putBoolean("haptics", on) }

    fun setAudio(ctx: Context, on: Boolean) =
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit { putBoolean("audio", on) }
}
