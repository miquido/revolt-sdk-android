package com.miquido.revolt

import android.content.Context
import com.miquido.revoltsdk.Revolt
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/** Created by MiQUiDO on 02.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
@Module
class AppModule {
    @Provides
    fun provideContext(application: RevoltApplication): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideRevoltSdk(context: Context): Revolt {
        return Revolt.Builder()
                .with(context)
                .secretKey("aaa")
                .endpoint("aa")
                .build()
    }
}
