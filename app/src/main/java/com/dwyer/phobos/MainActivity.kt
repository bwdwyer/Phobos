package com.dwyer.phobos

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.util.Log
import java.io.IOException

import kotlinx.android.synthetic.activity_main.*

public class MainActivity : ActionBarActivity() {
    private final val TAG = "MainActivity"
    private var _mp: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hello_text.setText("Tuning Radio to the 60s")

        _mp = MediaPlayer()
        try {
//            _mp!!.setDataSource("http://209.15.212.52:4130/") // 60's
            _mp!!.setDataSource("http://209.15.212.52:4132/") // 70's
            _mp!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
//            _mp!!.prepare()
            _mp!!.setOnPreparedListener({
                _mp!!.start()
            })
            _mp!!.prepareAsync()
        } catch (e: IOException) {
            Log.e(TAG, e.toString(), e)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _mp?.stop()
    }

}
