package com.thehurnes.helloratpack.domain

import groovy.transform.Canonical
import groovy.transform.CompileStatic
import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id

@Canonical
@CompileStatic
@Entity(noClassnameStored = true)
class Thing {

    @Id
    ObjectId id

    String name

}
