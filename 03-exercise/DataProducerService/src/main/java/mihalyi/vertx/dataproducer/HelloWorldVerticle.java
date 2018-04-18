package mihalyi.vertx.dataproducer;

import io.vertx.core.AbstractVerticle;


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
                .listen(35000, result -> {
                    if (result.succeeded()) {
                        System.out.println("Server started, listening on http://localhost:35000");
                    } else {
                        System.out.println("Cannot start the server: " + result.cause());
                    }
                });
    }
}
