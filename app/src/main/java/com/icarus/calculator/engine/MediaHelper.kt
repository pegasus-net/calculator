package com.icarus.calculator.engine

import a.icarus.utils.Icarus
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import javax.inject.Inject

class MediaHelper @Inject constructor() : LifecycleObserver {
    private val media: MediaPlayer = MediaPlayer()
    private val assets = Icarus.getContext().assets
    private var playList = ArrayList<Char>()

    init {
        media.setOnPreparedListener {
            it.start()
        }
        media.setOnErrorListener { it, _, _ ->
            it.reset()
            true
        }
        media.setOnCompletionListener {
            if (playList.isNotEmpty()) {
                playList.removeAt(0)
            }
            playFirstChar()
        }
    }

    fun play(text: String) {
        playList.clear()
        text.forEach {
            playList.add(it)
        }
        playFirstChar()
    }

    private fun playFirstChar() {
        if (playList.isNotEmpty()) {
            play(playList[0])
        }
    }

    private fun play(char: Char) {
        when (char) {
            '0', '零' -> play(assets.openFd("raw/zero.ogg"))
            '1', '一' -> play(assets.openFd("raw/one.ogg"))
            '2', '二' -> play(assets.openFd("raw/two.ogg"))
            '3', '三' -> play(assets.openFd("raw/three.ogg"))
            '4', '四' -> play(assets.openFd("raw/four.ogg"))
            '5', '五' -> play(assets.openFd("raw/five.ogg"))
            '6', '六' -> play(assets.openFd("raw/six.ogg"))
            '7', '七' -> play(assets.openFd("raw/seven.ogg"))
            '8', '八' -> play(assets.openFd("raw/eight.ogg"))
            '9', '九' -> play(assets.openFd("raw/nine.ogg"))
            '.', '点' -> play(assets.openFd("raw/point.ogg"))
            '十' -> play(assets.openFd("raw/ten.ogg"))
            '百' -> play(assets.openFd("raw/hundred.ogg"))
            '千' -> play(assets.openFd("raw/thousand.ogg"))
            '万' -> play(assets.openFd("raw/ten_thousand.ogg"))
            '亿' -> play(assets.openFd("raw/hundred_million.ogg"))
            '负' -> play(assets.openFd("raw/fuhao.ogg"))
            '+' -> play(assets.openFd("raw/plus.ogg"))
            '-' -> play(assets.openFd("raw/minus.ogg"))
            '*' -> play(assets.openFd("raw/multiply.ogg"))
            '/' -> play(assets.openFd("raw/divide.ogg"))
            '=' -> play(assets.openFd("raw/equal.ogg"))
            '%' -> play(assets.openFd("raw/percent.ogg"))
            '(', ')' -> play(assets.openFd("raw/bracket.ogg"))
            ']' -> play(assets.openFd("raw/ac.wav"))
            '[' -> play(assets.openFd("raw/del.wav"))
            else -> play(assets.openFd("raw/tap.mp3"))
        }

    }

    private fun play(file: AssetFileDescriptor) {
        media.reset()
        media.setDataSource(file.fileDescriptor, file.startOffset, file.length)
        media.prepareAsync()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        media.release()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        playList.clear()
        media.reset()
    }
}