package com.miquido.revoltsdk.internal.database

import com.miquido.revoltsdk.Event
import java.util.*

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal object DatabaseRepository {
    private var list: MutableList<Event> = Collections.synchronizedList(ArrayList())

    fun getEventsNumber(): Int {
        return list.size
    }

    fun getFirstEvents(number: Int): ArrayList<Event> {
        return ArrayList(list.take(number))
    }

    fun removeElements(number: Int){
        list = Collections.synchronizedList(ArrayList(list.drop(number)))
    }


    fun addEvent(event: Event) {
        list.add(event)
    }
}
