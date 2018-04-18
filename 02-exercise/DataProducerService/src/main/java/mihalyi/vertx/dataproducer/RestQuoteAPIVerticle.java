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
                    // Úloha 2 - ulož prijaté "quote" do quotes mapy
                    // Objekty Quote sú JSON objekty ktoré môžete dostať pomocou message.body()
                    // Do mapy ukladajte: quote.name -> quote
                    // ----
                    quotes.put(name, quote);
                    // ----
                });

        vertx.createHttpServer()
                .requestHandler(request -> {
                    HttpServerResponse response = request.response()
                            .putHeader("content-type", "application/json");

                    // Úloha 3 - request handler má vrátiť požadovanú quote podľa parametra name.
                    // Quote z mapy quotes je potrebné konvertovať do JSON pomocou quote.encodePrettily() a poslať pomocou response.end()
                    // Ak quote neexistuje, poslať status code 404
                    // Ak parameter name nie je zadaný, poslať všetky quotes ako JSON pomocou Json.encodePrettily()
                    request.getParam("name") // ----
                            // ----
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
