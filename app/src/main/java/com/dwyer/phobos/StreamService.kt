package com.dwyer.phobos

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager

/**
 * Created by briandwyer on 9/29/15.
 *
 */
public class StreamService : Service(), MediaPlayer.OnPreparedListener,
                                        MediaPlayer.OnErrorListener,
                                        MediaPlayer.OnCompletionListener {

    lateinit private var player: MediaPlayer

    private val musicBind = MusicBinder()

    override public fun onCreate() {
        player = MediaPlayer()
        player.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public fun playStream() {
        player.reset()
        player.setDataSource("http://209.15.212.52:4132/")
        player.setAudioStreamType(AudioManager.STREAM_MUSIC)
        player.prepareAsync()
    }

    public inner class MusicBinder : Binder() {
        public val service: StreamService = this@StreamService
    }

    override fun onBind(intent: Intent?): IBinder? = musicBind
    override fun onUnbind(intent: Intent?): Boolean {
        player.stop()
        player.release()
        return false
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onCompletion(mp: MediaPlayer?) {

    }

}
