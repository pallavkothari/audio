package com.pk.audio

import java.io.File


fun main(args : Array<String>) {
    if (args.size != 1) return
    Sound(File(args[0])).play()
}
