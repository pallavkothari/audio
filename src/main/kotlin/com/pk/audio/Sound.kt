package com.pk.audio

import javazoom.jl.player.Player
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.CyclicBarrier
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.LineEvent

/**
 * handles wav and mp3 files
 */
class Sound(private val soundFile: File) {
    private val barrier = CyclicBarrier(2)

    /**
     * plays the audio file all the way through.
     */
    fun play() = when (soundFile.extension) {
        "wav" -> playUsingJavaxSound()
        "mp3" -> playUsingJLayer()
        else -> throw Error("unsupported file format: ${soundFile.extension}")
    }

    private fun playUsingJLayer() {
        println("playing mp3...")
        val player = Player(FileInputStream(soundFile))
        player.play()
    }

    private fun playUsingJavaxSound() {
        println("playing wav file...")
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
