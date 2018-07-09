package com.miquido.revolt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.miquido.revoltsdk.Revolt
import com.miquido.revoltsdk.RevoltEvents
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
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
        sendEventButton.setOnClickListener { revoltSDK.sendEvent(RevoltEvents.fromKeyValue("MY_TYPE", "a", "b")) }
    }
}
