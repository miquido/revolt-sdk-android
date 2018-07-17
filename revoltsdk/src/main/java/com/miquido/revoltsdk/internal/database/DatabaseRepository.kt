package com.miquido.revoltsdk.internal.database

import com.miquido.revoltsdk.internal.model.EventModel
import java.util.*

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class DatabaseRepository {
    private val list: MutableList<EventModel> = ArrayList()

    fun getEventsNumber(): Int {
        return list.size
    }

    fun getFirstEvents(number: Int): ArrayList<EventModel> {
        return ArrayList(list.take(number))
    }

    fun removeEvents(number: Int) {
        list.subList(0, number).clear()
    }

    fun getFirstEvent(): EventModel? {
        return if (list.isEmpty()) null else list[0]
    }

    fun addEvent(event: EventModel) {
        list.add(event)
    }
}
