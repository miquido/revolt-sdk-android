package com.miquido.revolt

import android.content.Context
import rocks.revolt.Revolt
import rocks.revolt.RevoltLogLevel
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
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
        return Revolt.builder()
                .with(context)
                .trackingId("revolttest")
                .secretKey("ZjdMyTrmjVDC8Wr8")
                .endpoint("https://api.revolt.rocks/api/v1")
                .eventDelay(5, TimeUnit.SECONDS)
                .maxBatchSize(10)
                .logLevel(RevoltLogLevel.DEBUG)
                .build()
    }
}
