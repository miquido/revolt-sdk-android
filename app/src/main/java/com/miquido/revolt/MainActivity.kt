package com.miquido.revolt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.miquido.revoltsdk.Revolt
import com.miquido.revoltsdk.RevoltEvent
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

/** Created by MiQUiDO on 29.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var revoltSDK: Revolt

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendEventButton.setOnClickListener {
            Log.d("Revolt-SDK damian", " before events + ${System.currentTimeMillis()}")
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE1-${rand()}", "a", "b"))
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE2-${rand()}", "a", "b"))
            //Thread.sleep(15_100)
           /* revoltSDK.sendEvent(RevoltEvent("MY_TYPE3", "a", "b"))
            //Thread.sleep(3_100)
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE4", "a", "b"))
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE5", "a", "b"))
            //Thread.sleep(3_100)
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE6", "a", "b"))
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE7", "a", "b"))
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE8", "a", "b"))
            //Thread.sleep(3_100)
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE9", "a", "b"))
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE10", "a", "b"))
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE11", "a", "b"))
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE12", "a", "b"))*/
            Log.d("Revolt-SDK damian", " after events + ${System.currentTimeMillis()}")
        }
    }

    private fun rand(): Int {
        return Random().nextInt(100000)
    }
}
