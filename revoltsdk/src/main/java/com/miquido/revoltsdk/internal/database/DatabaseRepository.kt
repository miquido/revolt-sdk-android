package com.miquido.revoltsdk.internal.database

import com.miquido.revoltsdk.internal.model.EventModel
import com.miquido.revoltsdk.internal.toEntity
import com.miquido.revoltsdk.internal.toModel

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class DatabaseRepository(private val eventsDao: EventsDao) {

    fun getEventsNumber(): Int {
        return eventsDao.countAllEvents()
    }

    fun getFirstEvents(number: Int): List<EventModel>? {
        return eventsDao.getFirstElements(number)?.map { it.toModel() }
    }

    fun removeEvents(number: Int) {
        eventsDao.removeEvents(number)
    }

    fun getFirstEvent(): EventModel? {
        return eventsDao.getFirstElement()?.toModel()
    }

    fun addEvent(event: EventModel) {
        eventsDao.insert(event.toEntity())
    }


}
