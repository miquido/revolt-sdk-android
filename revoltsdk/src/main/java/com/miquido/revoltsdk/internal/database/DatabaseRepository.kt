package com.miquido.revoltsdk.internal.database

import com.google.gson.JsonObject
import com.miquido.revoltsdk.internal.asJsonObject
import com.miquido.revoltsdk.internal.model.EventModel
import java.util.*

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class DatabaseRepository(private val eventsDao: EventsDao) {

    fun getEventsNumber(): Int {
        return eventsDao.countAllEvents()
    }

    fun getFirstEvents(number: Int): List<JsonObject> {
        return eventsDao.getFirstElements(number)?.map { it.eventData.asJsonObject() } ?: Collections.emptyList()
    }

    fun removeEvents(number: Int) {
        eventsDao.removeEvents(number)
    }

    fun getFirstEventTimestamp(): Long? {
        return eventsDao.getFirstElement()?.timestamp
    }

    fun addEvent(event: EventModel) {
        eventsDao.insert(event.toEntity())
    }
}

internal fun EventModel.toEntity(): EventEntity {
    return EventEntity(this.createJson().toString(), this.getTimestamp())
}
