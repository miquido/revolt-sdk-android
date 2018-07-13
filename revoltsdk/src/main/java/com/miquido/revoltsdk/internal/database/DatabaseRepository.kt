package com.miquido.revoltsdk.internal.database

import com.miquido.revoltsdk.Event
import com.miquido.revoltsdk.internal.model.RevoltModel
import java.util.*

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal object DatabaseRepository {
    private var list: MutableList<RevoltModel> = Collections.synchronizedList(ArrayList())

    fun getEventsNumber(): Int {
        return list.size
    }

    fun getFirstEvents(number: Int): ArrayList<RevoltModel> {
        return ArrayList(list.take(number))
    }

    fun removeElements(number: Int){
        list = Collections.synchronizedList(ArrayList(list.drop(number)))
    }


    fun addEvent(event: RevoltModel) {
        list.add(event)
    }
}
