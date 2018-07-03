package com.miquido.revolt

import dagger.Module
import dagger.android.ContributesAndroidInjector

/** Created by MiQUiDO on 02.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [])
    abstract fun mainActivity(): MainActivity
}
