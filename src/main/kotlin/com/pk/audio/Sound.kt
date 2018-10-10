package com.pk.audio

import java.io.File
import java.util.concurrent.CyclicBarrier
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.LineEvent


class Sound(private val soundFile: File) {
    private val barrier = CyclicBarrier(2)

    /**
     * plays the audio file all the way through.
     */
    fun play() {
        AudioSystem.getAudioInputStream(soundFile).use { audioIn ->
            AudioSystem.getClip().use {
                listenForEndOf(it)
                it.open(audioIn)
                it.start()
                barrier.await()
                Unit
            }
        }
    }

    private fun listenForEndOf(clip: Clip) {
        clip.addLineListener {
            if (it.type === LineEvent.Type.STOP) {
                println("reached the end of the clip")
                barrier.await()
            }
        }
    }
}