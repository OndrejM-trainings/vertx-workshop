package mihalyi.vertx.dataconsumer;

import mihalyi.vertx.launcher.VertxHelper;

public class Launcher {

    public static void main(String[] args) {
        VertxHelper.runVertxWithVerticles(MarketDataLoggerVeticle.class);
    }
}
