import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.ConnectionString
import com.mongodb.MongoException
import com.mongodb.WriteConcernResult
import com.mongodb.async.SingleResultCallback
import com.mongodb.async.client.MongoCollection
import com.thehurnes.inject.HelloRatpackModule
import com.thehurnes.inject.MongoModule
import com.thehurnes.repositories.ThingRepository
import org.bson.Document
import ratpack.jackson.JacksonModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

ratpack {

    bindings {
        add MongoModule, { config ->
            config.connectionString = new ConnectionString("mongodb://localhost:27017/")
            config.dbName = "hello-ratpack"
        }

        add JacksonModule
        add HelloRatpackModule

        init { ObjectMapper objectMapper, Set<Module> modules ->
            objectMapper.registerModules(modules)
        }
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

        get("things/:id") { ThingRepository thingRepo ->
            thingRepo.get(pathTokens.get("id")).then { thing ->
                render json(thing)
            }
        }

    }

}
