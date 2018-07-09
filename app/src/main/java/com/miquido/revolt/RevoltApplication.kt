package com.miquido.revolt

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/** Created by MiQUiDO on 02.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class RevoltApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent.builder().create(this)
}