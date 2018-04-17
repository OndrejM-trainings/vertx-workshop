package mihalyi.vertx.dataproducer;

import mihalyi.vertx.launcher.*;

public class Launcher {

    public static void main(String[] args) {
        VertxHelper.runVertxWithVerticle(GeneratorConfigVerticle.class);
    }

}
