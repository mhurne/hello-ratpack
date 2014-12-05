import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.MongoClientURI
import com.thehurnes.helloratpack.inject.HelloRatpackModule
import com.thehurnes.helloratpack.inject.MongoModule
import com.thehurnes.helloratpack.resources.ThingsResource
import ratpack.jackson.JacksonModule

import static ratpack.groovy.Groovy.ratpack

ratpack {

    bindings {
        add MongoModule, { config ->
            config.connectionString = new MongoClientURI("mongodb://localhost:27017/")
            config.dbName = "hello-ratpack"
        }

        add JacksonModule
        add HelloRatpackModule

        init { ObjectMapper objectMapper, Set<Module> modules ->
            objectMapper.registerModules(modules)
        }
    }

    handlers {

        prefix("api/things") {
            handler chain(registry.get(ThingsResource))
        }

    }

}
