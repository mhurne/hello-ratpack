package com.thehurnes.helloratpack.repositories

import com.thehurnes.helloratpack.domain.Thing
import groovy.transform.CompileStatic
import org.mongodb.morphia.Datastore
import ratpack.exec.ExecControl

import javax.inject.Inject

@CompileStatic
class ThingRepository extends Repository<Thing> {

    @Inject
    ThingRepository(ExecControl execControl, Datastore datastore) {
        super(execControl, datastore, Thing)
    }

}
