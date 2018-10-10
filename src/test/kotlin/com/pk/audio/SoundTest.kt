package com.pk.audio

import com.google.common.io.Resources
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.io.File

class SoundTest {

    @Test
    fun testPlayingSound() {
        val url = Resources.getResource("chime.wav")
        val wav = File(url.toURI())
        assertThat(wav.exists()).isTrue()
        Sound(wav).play()
    }
}
