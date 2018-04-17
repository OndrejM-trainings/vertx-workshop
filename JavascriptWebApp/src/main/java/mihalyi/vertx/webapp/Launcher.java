package mihalyi.vertx.webapp;

import mihalyi.vertx.launcher.VertxHelper;

public class Launcher {

    public static void main(String[] args) {
        VertxHelper.runVertxWithVerticles("webappverticle.js");
    }
}
