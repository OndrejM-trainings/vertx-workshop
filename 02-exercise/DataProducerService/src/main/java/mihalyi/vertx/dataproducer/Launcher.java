package mihalyi.vertx.dataproducer;

import io.vertx.core.Vertx;

public class Launcher {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        VertxHelper.deployVerticles(vertx, DataProducerConfigVerticle.class.getName());
    }

}
