package mihalyi.vertx.dataconsumer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class MarketDataLoggerVeticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.eventBus().<JsonObject>consumer("market")
                .handler(message -> {
                    final JsonObject quote = message.body();
                    final String name = quote.getString("name");

                    System.out.println(String.format("Quote %s:%s", name, quote));
                });

    }
}
