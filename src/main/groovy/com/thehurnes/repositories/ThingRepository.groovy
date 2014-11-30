package com.thehurnes.repositories

import com.mongodb.async.client.MongoCollection
import com.mongodb.async.client.MongoDatabase
import com.thehurnes.domain.Thing
import groovy.transform.CompileStatic
import org.bson.Document
import ratpack.exec.ExecControl

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@CompileStatic
class ThingRepository extends Repository<Thing> {

    @Inject
    ThingRepository(ExecControl execControl, MongoDatabase db) {
        super(execControl, db, Thing)
    }

    @Override
    protected MongoCollection<Document> getCollection() {
        return db.getCollection('Thing')
    }

    @Override
    protected Thing toEntity(Document document) {
        return new Thing(document.getObjectId('_id'), document.getString('name'))
    }

}
