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
                    // TODO Populate the `quotes` map with the received quote
                    // Quotes are json objects you can retrieve from the message body
                    // The map is structured as follows: name -> quote
                    // ----
                    final JsonObject quote = message.body();
                    final String name = quote.getString("name");
                    quotes.put(name, quote);
                    // ----
                });

        vertx.createHttpServer()
                .requestHandler(request -> {
                    HttpServerResponse response = request.response()
                            .putHeader("content-type", "application/json");

                    // TODO
                    // The request handler returns a specific quote if the `name` parameter is set, or the whole map if none.
                    // To write the response use: `request.response().end(content)`
                    // Responses are returned as JSON, so don't forget the "content-type": "application/json" header.
                    // If the symbol is set but not found, you should return 404.
                    // Once the request handler is set,
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

                    // ----
                    // ----
                })
                .rxListen(config().getInteger("http.port"))
                .subscribe(
                        result -> {
                            System.out.println("Server started, listening on http://localhost:" + config().getInteger("http.port"));
                        },
                        ex -> {
                            System.out.println("Cannot start the server: " + ex);
                        });
    }
}
