package com.dwyer.phobos

import android.media.AudioManager
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.activity_main.hello_text
import java.io.IOException
import java.util.*

public class MainActivity : AppCompatActivity() {
    private final val TAG = "PHOBOS_MAIN"
    private var _mp: MediaPlayer? = null
    private var _mmr: MediaMetadataRetriever? = null

//    val channel = "http://209.15.212.52:4130/" // 60s
    val channel = "http://209.15.212.52:4132/" // 70s

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hello_text.setText("Tuning Radio to the 70s")

        openAudioChannel(channel)
//        openAudioMetaDataChannel(channel)
    }

    private fun openAudioChannel(url: String) {
        _mp = MediaPlayer()

        _mp!!.setOnInfoListener { mp, what, extra ->
            Log.i(TAG, "OnInfoChangeCalled $what $extra")
            openAudioMetaDataChannel(channel)
            return@setOnInfoListener true
        }

        try {
            _mp!!.setDataSource(url)
            _mp!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
//            _mp!!.prepare()
            _mp!!.setOnPreparedListener({
                _mp!!.start()
                openAudioMetaDataChannel(channel)
            })
            _mp!!.prepareAsync()
        } catch(e: IOException) {
            Log.e(TAG, e.toString(), e)
        } finally {

        }
    }

    private fun openAudioMetaDataChannel(url: String) {
        _mmr = MediaMetadataRetriever()

        _mmr!!.setDataSource(url, HashMap<String, String>())

        Log.i(TAG, "MMR Object: $_mmr")

        val artist = _mmr!!.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
        val title  = _mmr!!.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)

        Log.i(TAG, "Artist: $artist")
        Log.i(TAG, "Title: $title")

        _mmr!!.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        _mp?.stop()
    }

}
