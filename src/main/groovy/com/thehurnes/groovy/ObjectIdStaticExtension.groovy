package com.thehurnes.groovy

import org.bson.types.ObjectId

class ObjectIdStaticExtension {

    static ObjectId of(ObjectId selfType, Object o) {
        if (o == null) {
            return null
        }
        if (o instanceof ObjectId) {
            return o
        }
        if (o instanceof String) {
            return new ObjectId(o)
        }
        return null
    }

}
