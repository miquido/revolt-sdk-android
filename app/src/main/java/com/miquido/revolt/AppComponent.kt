package com.miquido.revolt

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/** Created by MiQUiDO on 02.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuilder::class,
    AppModule::class])
interface AppComponent : AndroidInjector<RevoltApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<RevoltApplication>()
}

