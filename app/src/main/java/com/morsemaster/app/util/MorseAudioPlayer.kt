package com.morsemaster.app.util

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.sin

object MorseAudioPlayer {
    private const val SAMPLE_RATE = 44100
    private const val FREQ_HZ = 700.0
    private const val DOT_MS = 80
    private const val DASH_MS = DOT_MS * 3
    private const val GAP_MS = DOT_MS
    private const val LETTER_GAP_MS = DOT_MS * 3

    suspend fun play(morse: String) = withContext(Dispatchers.IO) {
        val track = AudioTrack.Builder()
            .setAudioFormat(
                AudioFormat.Builder()
                    .setSampleRate(SAMPLE_RATE)
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setTransferMode(AudioTrack.MODE_STREAM)
            .setBufferSizeInBytes(AudioTrack.getMinBufferSize(
                SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT
            ))
            .build()
        track.play()
        morse.forEach { ch ->
            when (ch) {
                '.' -> { writeTone(track, DOT_MS); writeSilence(track, GAP_MS) }
                '-' -> { writeTone(track, DASH_MS); writeSilence(track, GAP_MS) }
                ' ' -> writeSilence(track, LETTER_GAP_MS)
            }
        }
        track.stop()
        track.release()
    }

    private fun writeTone(track: AudioTrack, durationMs: Int) {
        val samples = SAMPLE_RATE * durationMs / 1000
        val buf = ShortArray(samples) { i ->
            val angle = 2.0 * Math.PI * FREQ_HZ * i / SAMPLE_RATE
            // fade in/out 5ms to avoid clicks
            val fade = when {
                i < 220 -> i / 220.0
                i > samples - 220 -> (samples - i) / 220.0
                else -> 1.0
            }
            (sin(angle) * 32767 * fade).toInt().toShort()
        }
        track.write(buf, 0, buf.size)
    }

    private fun writeSilence(track: AudioTrack, durationMs: Int) {
        val samples = SAMPLE_RATE * durationMs / 1000
        track.write(ShortArray(samples), 0, samples)
    }
}
