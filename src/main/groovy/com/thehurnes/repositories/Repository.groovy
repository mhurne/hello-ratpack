package com.thehurnes.repositories

import com.mongodb.MongoException
import com.mongodb.async.SingleResultCallback
import com.mongodb.async.client.MongoCollection
import com.mongodb.async.client.MongoDatabase
import org.bson.Document
import org.bson.types.ObjectId
import ratpack.exec.ExecControl
import ratpack.exec.Promise

import javax.inject.Inject

abstract class Repository<T> {

    protected final ExecControl execControl
    protected final MongoDatabase db
    protected final Class<T> entityClass

    @Inject
    Repository(ExecControl execControl, MongoDatabase db, Class<T> entityClass) {
        this.execControl = execControl
        this.db = db
        this.entityClass = entityClass
    }

    Promise<T> get(def id) {
        return execControl.promise { fulfiller ->
            collection.find(new Document().append('_id', ObjectId.of(id))).first({ Document document, MongoException e ->
                if (e) {
                    fulfiller.error(e)
                } else {
                    fulfiller.success(toEntity(document))
                }
            } as SingleResultCallback)
        }
    }

    abstract protected MongoCollection<Document> getCollection()
    abstract protected T toEntity(Document document)

}
