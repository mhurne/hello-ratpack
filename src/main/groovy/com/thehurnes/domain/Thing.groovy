package com.thehurnes.domain

import groovy.transform.Canonical
import groovy.transform.CompileStatic
import org.bson.types.ObjectId

@Canonical
@CompileStatic
class Thing {

    ObjectId id
    String name

}
