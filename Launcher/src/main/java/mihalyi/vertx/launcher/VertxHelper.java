package mihalyi.vertx.launcher;

import io.vertx.core.*;
import io.vertx.core.json.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.*;

public final class VertxHelper {

    // util class
    private VertxHelper() {
    }
    
    public static void runVertxWithVerticle(Class<? extends AbstractVerticle> verticle) {
        final VertxOptions options = VertxHelper.applyDefaultVertxOptions(new VertxOptions());
        Vertx.clusteredVertx(options, ar -> {
            ar.map(vertx -> {
                final DeploymentOptions deploymentOptions = readAndSetConfig(new DeploymentOptions());
                vertx.deployVerticle(verticle.getName(), deploymentOptions);
                return null;
            }).otherwise(t -> {
                Logger.getLogger(VertxHelper.class.getName())
                        .log(Level.SEVERE, "Vert.x failed to start with verticle " + verticle.getName()
                                + " - " + t.getMessage(), t);
                return null;
            });
        });
        
    }
    
    public static VertxOptions applyDefaultVertxOptions(VertxOptions options) {
        options.setClustered(true)
        .setClusterHost("127.0.0.1");
        return options;
    }

    public static DeploymentOptions readAndSetConfig(DeploymentOptions deploymentOptions) {

        if (deploymentOptions.getConfig() == null) {
            deploymentOptions.setConfig(new JsonObject());
        }

        File conf = new File("src/conf/config.json");
        deploymentOptions.getConfig().mergeIn(getConfiguration(conf));
        return deploymentOptions;
    }

    private static JsonObject getConfiguration(File config) {
        JsonObject conf = new JsonObject();
        if (config.isFile()) {
            System.out.println("Reading config file: " + config.getAbsolutePath());
            try (Scanner scanner = new Scanner(config).useDelimiter("\\A")) {
                String sconf = scanner.next();
                try {
                    conf = new JsonObject(sconf);
                } catch (DecodeException e) {
                    System.err.println("Configuration file " + sconf + " does not contain a valid JSON object");
                }
            } catch (FileNotFoundException e) {
                // Ignore it.
            }
        } else {
            System.out.println("Config file not found " + config.getAbsolutePath());
        }
        return conf;
    }

}
