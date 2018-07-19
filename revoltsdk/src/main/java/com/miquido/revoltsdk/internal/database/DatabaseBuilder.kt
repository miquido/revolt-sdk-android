package com.miquido.revoltsdk.internal.database

import android.arch.persistence.room.Room
import android.content.Context

/** Created by MiQUiDO on 18.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class DatabaseBuilder(private val context: Context) {
    fun getEventsDao(): EventsDao {
        return Room.databaseBuilder(context.applicationContext,
                RevoltDatabase::class.java, "events.db")
                .build().eventsDao()
    }
}
