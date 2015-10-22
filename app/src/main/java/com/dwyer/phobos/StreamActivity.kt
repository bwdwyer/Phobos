package com.dwyer.phobos

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder

import kotlinx.android.synthetic.stream_test.*

/**
 * Created by briandwyer on 9/29/15.
 *
 */
public class StreamActivity : Activity() {

    private var streamService: StreamService? = null
    private var musicBound = false;

    public val musicConnection: ServiceConnection = object: ServiceConnection {

        override public fun onServiceConnected(name: ComponentName, service: IBinder) {
            musicBound = true

            val binder = service as StreamService.MusicBinder
            streamService = binder.service

            streamService!!.playStream()
        }

        override public fun onServiceDisconnected(name: ComponentName) {
            musicBound = false
            streamService = null
            tv_last_error.text = "Service was disconnected \n$name"
        }
    };

    public fun startService() {
        val playIntent = Intent(this@StreamActivity, StreamService::class.java)
        bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE)
        startService(playIntent)
    }

    override protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stream_test)

        btn_refresh.setOnClickListener {
            if(musicBound) {
                streamService?.playStream()
            } else {
                startService()
            }
        }
    }

    override protected fun onStart() {
        super.onStart()
        if(!musicBound) {
            startService()
        }
    }

}