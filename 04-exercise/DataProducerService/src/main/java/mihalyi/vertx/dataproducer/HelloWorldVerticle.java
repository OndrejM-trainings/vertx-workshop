package mihalyi.vertx.dataproducer;

import io.vertx.core.AbstractVerticle;
import io.vertx.reactivex.core.Future;


/**
 * Tento Verticle vytvorí Hello World REST endpoint
 *
 */
public class HelloWorldVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        vertx.createHttpServer()
                .requestHandler(request -> {
                    request.response()
                            .putHeader("content-type", "text/plain")
                            .end("Hello world");

                })
                // Úloha 1 - prepíš callback pomocou Vert.x Future a jej met=ody completer{} 
//                .listen(35000, result -> {
//                    if (result.succeeded()) {
//                        System.out.println("Server started, listening on http://localhost:35000");
//                    } else {
//                        System.out.println("Cannot start the server: " + result.cause());
//                    }
//                });
                .listen(35000, Future.future().completer())
                
                // Úloha 2 - prepíš riešenie pomocou RxJava subscribe() a pomocou AbstractVerticle z package io.vertx.reactivex.core
    }
}
