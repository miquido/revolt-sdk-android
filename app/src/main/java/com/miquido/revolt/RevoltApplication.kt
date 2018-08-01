package com.miquido.revolt

import com.facebook.stetho.Stetho
import rocks.revolt.Revolt
import rocks.revolt.RevoltActivityLifecycleCallbacks
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

/** Created by MiQUiDO on 02.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class RevoltApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent.builder().create(this)

    @Inject
    lateinit var revoltSDK: Revolt

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        registerActivityLifecycleCallbacks(RevoltActivityLifecycleCallbacks(revoltSDK))
    }

}
