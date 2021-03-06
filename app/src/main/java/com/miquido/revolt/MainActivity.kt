package com.miquido.revolt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import rocks.revolt.*
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
            Log.d("Revolt-SDK", " before events + ${System.currentTimeMillis()}")
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE1-${rand()}", "a", "b"))
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE2-${rand()}", "a", "b"))
            revoltSDK.sendEvent(buildUserSignedInEvent("12345"))
            revoltSDK.sendEvent(buildUserProfileEvent("12345")
                    .birthYear(1990)
                    .gender(Gender.M)
                    .city("Krakow")
                    .customProperties(
                            "heightCm" to 175,
                            "weightKg" to 70,
                            "eyeColor" to "blue")
                    .build())
            revoltSDK.sendEvent(buildUserSignedOutEvent("12345"))
            Log.d("Revolt-SDK", " after events + ${System.currentTimeMillis()}")
        }

        sendCustomButton.setOnClickListener {
            revoltSDK.sendEvent(RevoltEvent("MY_TYPE-${rand()}", key.text.toString(), value.text.toString()))
        }
    }

    private fun rand(): Int {
        return Random().nextInt(100000)
    }
}
