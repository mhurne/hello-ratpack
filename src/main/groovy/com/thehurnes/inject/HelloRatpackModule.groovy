package com.thehurnes.inject

import com.fasterxml.jackson.databind.Module
import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import com.thehurnes.repositories.ThingRepository

class HelloRatpackModule extends AbstractModule {

    @Override
    protected void configure() {
        def jacksonModuleBinder = Multibinder.newSetBinder(binder(), Module)
        jacksonModuleBinder.addBinding().to(com.commercehub.jackson.datatype.mongo.MongoModule)

        bind(ThingRepository)
    }

}
