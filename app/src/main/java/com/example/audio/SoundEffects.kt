package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sin

object SoundEffects {
    private val scope = CoroutineScope(Dispatchers.Default)

    fun vibrate(context: Context, durationMs: Long = 100) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(
                    VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(durationMs)
                }
            }
        } catch (_: Exception) {
            // Ignore if vibration unavailable
        }
    }

    /**
     * Plays authentic KSRTC musical air horn (sequence of chords: 520Hz, 650Hz, 780Hz)
     */
    fun playKsrtcAirHorn(context: Context) {
        vibrate(context, 250)
        scope.launch {
            val sampleRate = 44100
            val frequencies = listOf(
                Pair(520.0, 120),
                Pair(660.0, 120),
                Pair(520.0, 80),
                Pair(780.0, 260)
            )
            for ((freq, durationMs) in frequencies) {
                playTone(freq, durationMs, sampleRate, volume = 0.85f)
            }
        }
    }

    /**
     * Bullet thump / dug-dug beat
     */
    fun playBulletThump(context: Context) {
        vibrate(context, 120)
        scope.launch {
            val sampleRate = 44100
            for (i in 0 until 4) {
                playTone(110.0, 50, sampleRate, volume = 0.9f)
                playTone(75.0, 70, sampleRate, volume = 0.8f)
                kotlinx.coroutines.delay(100)
            }
        }
    }

    /**
     * Auto Rickshaw beep horn
     */
    fun playAutoHorn(context: Context) {
        vibrate(context, 150)
        scope.launch {
            val sampleRate = 44100
            playTone(850.0, 90, sampleRate, volume = 0.75f)
            kotlinx.coroutines.delay(50)
            playTone(850.0, 160, sampleRate, volume = 0.8f)
        }
    }

    /**
     * Kerala Police wailing siren
     */
    fun playPoliceSiren(context: Context) {
        vibrate(context, 300)
        scope.launch {
            val sampleRate = 44100
            val numSteps = 20
            // Sweep up
            for (i in 0..numSteps) {
                val freq = 600.0 + (i * 35.0)
                playTone(freq, 25, sampleRate, volume = 0.8f)
            }
            // Sweep down
            for (i in numSteps downTo 0) {
                val freq = 600.0 + (i * 35.0)
                playTone(freq, 25, sampleRate, volume = 0.8f)
            }
        }
    }

    /**
     * Pazham Pori / Chai bonus pickup chime
     */
    fun playPickupChime(context: Context) {
        vibrate(context, 60)
        scope.launch {
            val sampleRate = 44100
            playTone(587.33, 70, sampleRate, volume = 0.7f) // D5
            playTone(739.99, 70, sampleRate, volume = 0.75f) // F#5
            playTone(880.00, 120, sampleRate, volume = 0.8f) // A5
        }
    }

    /**
     * Crash / bump impact sound
     */
    fun playCrashSound(context: Context) {
        vibrate(context, 400)
        scope.launch {
            val sampleRate = 22050
            val durationMs = 250
            val numSamples = (sampleRate * durationMs / 1000)
            val buffer = ShortArray(numSamples)
            for (i in 0 until numSamples) {
                // Noise decay
                val decay = 1.0f - (i.toFloat() / numSamples)
                val noise = (Math.random() * 2.0 - 1.0) * Short.MAX_VALUE * decay * 0.75f
                buffer[i] = noise.toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }
            playPcmBuffer(buffer, sampleRate)
        }
    }

    private fun playTone(freq: Double, durationMs: Int, sampleRate: Int, volume: Float = 0.8f) {
        val numSamples = (sampleRate * durationMs / 1000)
        val buffer = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val angle = 2.0 * Math.PI * i / (sampleRate / freq)
            val envelope = if (i < numSamples * 0.1) {
                (i / (numSamples * 0.1)).toFloat()
            } else if (i > numSamples * 0.8) {
                ((numSamples - i) / (numSamples * 0.2)).toFloat()
            } else {
                1.0f
            }
            val sample = (sin(angle) * Short.MAX_VALUE * volume * envelope).toInt()
            buffer[i] = sample.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
        }
        playPcmBuffer(buffer, sampleRate)
    }

    private fun playPcmBuffer(buffer: ShortArray, sampleRate: Int) {
        try {
            val minBufSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            val trackSize = maxOf(buffer.size * 2, minBufSize)

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(trackSize)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(buffer, 0, buffer.size)
            audioTrack.play()
            Thread.sleep((buffer.size * 1000L / sampleRate) + 20)
            audioTrack.stop()
            audioTrack.release()
        } catch (_: Exception) {
            // Audio output graceful fallback
        }
    }
}
