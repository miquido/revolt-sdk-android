package com.miquido.revoltsdk.internal.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/** Created by MiQUiDO on 18.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
@Database(entities = [(EventEntity::class)], version = 1, exportSchema = false)
internal abstract class RevoltDatabase : RoomDatabase() {
    abstract fun eventsDao(): EventsDao
}
