package com.thehurnes.helloratpack.inject

import com.google.inject.Provides
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.thehurnes.helloratpack.domain.Thing
import groovy.transform.CompileStatic
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia
import ratpack.guice.ConfigurableModule

import javax.inject.Singleton

@CompileStatic
class MongoModule extends ConfigurableModule<Config> {

    static class Config {

        MongoClientURI connectionString
        String dbName

    }

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    MongoClient mongoClient(Config config) {
        return new MongoClient(config.connectionString)
    }

    @Provides
    @Singleton
    Morphia morphia() {
        def morphia = new Morphia()
        morphia.mapPackageFromClass(Thing)
        return morphia
    }

    @Provides
    @Singleton
    Datastore datastore(Config config, MongoClient mongoClient, Morphia morphia) {
        return morphia.createDatastore(mongoClient, config.dbName)
    }

}
