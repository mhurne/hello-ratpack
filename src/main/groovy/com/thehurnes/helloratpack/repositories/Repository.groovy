package com.thehurnes.helloratpack.repositories

import groovy.transform.CompileStatic
import org.bson.types.ObjectId
import org.mongodb.morphia.Datastore
import ratpack.exec.ExecControl
import ratpack.exec.Promise

import javax.inject.Inject

@CompileStatic
abstract class Repository<T> {

    protected final ExecControl execControl
    protected final Datastore datastore
    protected final Class<T> entityClass

    @Inject
    Repository(ExecControl execControl, Datastore datastore, Class<T> entityClass) {
        this.execControl = execControl
        this.datastore = datastore
        this.entityClass = entityClass
    }

    Promise<List<T>> findAll() {
        return execControl.blocking {
            return datastore.find(entityClass).asList()
        }
    }

    Promise<T> get(ObjectId id) {
        return execControl.blocking {
            return datastore.get(entityClass, id)
        }
    }

    Promise<Void> save(T entity) {
        return execControl.blocking {
            datastore.save(entity)
            return null
        }
    }

}
