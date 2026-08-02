package com.morsemaster.app.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.content.ContextCompat

object HapticFeedback {
    /** Short buzz for correct answer */
    fun correct(context: Context) = vibrate(context, longArrayOf(0, 60))

    /** Double buzz for wrong answer */
    fun wrong(context: Context) = vibrate(context, longArrayOf(0, 80, 60, 80))

    /** Celebratory pattern for perfect lesson */
    fun celebrate(context: Context) = vibrate(context, longArrayOf(0, 50, 30, 50, 30, 100))

    private fun vibrate(context: Context, pattern: LongArray) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vm.defaultVibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                @Suppress("DEPRECATION")
                val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                @Suppress("DEPRECATION")
                v.vibrate(pattern, -1)
            }
        } catch (_: Exception) { /* vibration not available */ }
    }
}
