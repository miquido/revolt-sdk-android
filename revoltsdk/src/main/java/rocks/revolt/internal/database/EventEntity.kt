package rocks.revolt.internal.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import rocks.revolt.internal.database.EventEntity.Companion.TABLE_NAME

/** Created by MiQUiDO on 18.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
@Entity(tableName = TABLE_NAME)
internal data class EventEntity(val eventData: String,
                                val timestamp: Long) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        const val TABLE_NAME = "event"
    }
}

