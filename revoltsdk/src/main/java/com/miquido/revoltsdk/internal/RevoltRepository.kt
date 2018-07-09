package com.miquido.revoltsdk.internal

import com.miquido.revoltsdk.RevoltEvent
import com.miquido.revoltsdk.internal.database.DatabaseRepository
import com.miquido.revoltsdk.internal.network.BackendRepository

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class RevoltRepository(private val backendRepository: BackendRepository,
                       private val databaseRepository: DatabaseRepository) : EventsRepository {

    override fun addEvent(revoltEvent: RevoltEvent) {
        revoltEvent.generateMetadata()
        backendRepository.addEvent(revoltEvent)
    }
}
