package com.thehurnes.helloratpack.resources

import com.thehurnes.helloratpack.domain.Thing
import com.thehurnes.helloratpack.repositories.ThingRepository
import org.bson.types.ObjectId
import ratpack.func.Action
import ratpack.groovy.Groovy
import ratpack.handling.Chain

import javax.inject.Inject

import static ratpack.jackson.Jackson.json

class ThingsResource implements Action<Chain> {

    private final ThingRepository thingRepository

    @Inject
    ThingsResource(ThingRepository thingRepository) {
        this.thingRepository = thingRepository
    }

    @Override
    void execute(Chain chain) {
        Groovy.chain(chain) {

            handler(":id") {
                byMethod {
                    get {
                        ObjectId id = null
                        try {
                            id = new ObjectId(pathTokens.get("id"))
                        } catch (IllegalArgumentException ignored) {
                            clientError(404)
                        }

                        if (id) {
                            thingRepository.get(id).then { Thing thing ->
                                render json(thing)
                            }
                        }
                    }
                }
            }

            handler {
                byMethod {
                    get {
                        thingRepository.findAll().then { List<Thing> things ->
                            render json(things)
                        }
                    }

                    post {
                        def thing = new Thing(name: "Thing One")
                        thingRepository.save(thing).then {
                            render json(thing)
                        }
                    }
                }
            }

        }
    }

}
