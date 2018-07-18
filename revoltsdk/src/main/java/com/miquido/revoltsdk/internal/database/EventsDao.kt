package com.miquido.revoltsdk.internal.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


/** Created by MiQUiDO on 18.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
@Dao
internal interface EventsDao {
    @Query("SELECT COUNT(*) FROM ${EventEntity.TABLE_NAME}")
    fun countAllEvents(): Int

    @Insert
    fun insert(eventEntity: EventEntity): Long

    @Query("SELECT * FROM ${EventEntity.TABLE_NAME} ORDER BY id ASC LIMIT 1")
    fun getFirstElement(): EventEntity?

    @Query("SELECT * FROM ${EventEntity.TABLE_NAME} ORDER BY id ASC LIMIT :number")
    fun getFirstElements(number: Int): List<EventEntity>?

    @Query("DELETE FROM ${EventEntity.TABLE_NAME} WHERE id IN (SELECT id FROM ${EventEntity.TABLE_NAME} ORDER BY id ASC LIMIT :number)")
    fun removeEvents(number: Int)
}
