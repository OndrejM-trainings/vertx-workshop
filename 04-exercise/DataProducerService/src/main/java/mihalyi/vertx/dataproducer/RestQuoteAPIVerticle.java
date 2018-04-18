package mihalyi.vertx.dataproducer;

import io.vertx.reactivex.core.*;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.http.HttpServerResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * This verticle exposes a HTTP endpoint to retrieve the current / last values
 * of the maker data (quotes).
 *
 */
public class RestQuoteAPIVerticle extends AbstractVerticle {

    private Map<String, JsonObject> quotes = new HashMap<>();

    @Override
    public void start() throws Exception {
        vertx.eventBus().<JsonObject>consumer(DataProducerConfigVerticle.ADDRESS)
                .handler(message -> {
                    final JsonObject quote = message.body();
                    final String name = quote.getString("name");
                    quotes.put(name, quote);
                });

        vertx.createHttpServer()
                .requestHandler(request -> {
                    HttpServerResponse response = request.response()
                            .putHeader("content-type", "application/json");

                    final String name = request.getParam("name");
                    if (name != null) {
                        final JsonObject quote = quotes.get(name);
                        if (quote != null) {
                            response
                                    .end(quote.encodePrettily());
                        } else {
                            response.setStatusCode(404).end();
                        }
                    }
                    response
                            .end(Json.encodePrettily(quotes));
                })
                .listen(config().getInteger("http.port"), ar -> {
                    if (ar.succeeded()) {
                        System.out.println("Server started, listening on http://localhost:" + config().getInteger("http.port"));
                    } else {
                        System.out.println("Cannot start the server: " + ar.cause());
                    }
                });
    }
}
