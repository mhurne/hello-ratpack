import com.mongodb.ConnectionString
import com.mongodb.MongoException
import com.mongodb.WriteConcernResult
import com.mongodb.async.SingleResultCallback
import com.mongodb.async.client.MongoCollection
import com.thehurnes.inject.MongoModule
import org.bson.Document
import org.bson.types.ObjectId
import ratpack.jackson.JacksonModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

ratpack {

    bindings {
        add MongoModule, {
            it.connectionString = new ConnectionString("mongodb://localhost:27017/")
            it.dbName = "hello-ratpack"
        }
        add JacksonModule
    }

    handlers {

        handler("things") {
            byMethod {
                get { MongoCollection<Document> collection ->
                    promise { f ->
                        collection.find().into([], { List<Document> things, MongoException e ->
                            if (e) {
                                f.error(e)
                            } else {
                                f.success(things)
                            }
                        } as SingleResultCallback)
                    }.then { List<Document> things ->
                        render json(things)
                    }
                }

                post { MongoCollection<Document> collection ->
                    def thing = new Document().append('name', 'Thing One')
                    promise { f ->
                        collection.insertOne(thing, { WriteConcernResult result, MongoException e ->
                            if (e) {
                                f.error(e)
                            } else {
                                f.success(result)
                            }
                        } as SingleResultCallback)
                    }.then { WriteConcernResult result ->
                        render json(thing)
                    }
                }
            }
        }

        get("things/:id") { MongoCollection<Document> collection ->
            def id = new ObjectId(pathTokens.get("id"))
            promise { f ->
                collection.find(new Document().append('_id', id)).first({ Document thing, MongoException e ->
                    if (e) {
                        f.error(e)
                    } else {
                        f.success(thing)
                    }
                } as SingleResultCallback)
            }.then { Document thing ->
                render json(thing)
            }
        }

    }

}
