package mihalyi.vertx.launcher;

import io.vertx.core.*;
import io.vertx.core.json.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.*;

public final class VertxHelper {

    private static final Logger LOGGER = Logger.getLogger(VertxHelper.class.getName());

    // util class
    private VertxHelper() {
    }

    public static void runVertxWithVerticles(String... verticles) {
        final VertxOptions options = applyDefaultVertxOptions(new VertxOptions());
        io.vertx.reactivex.core.Vertx.rxClusteredVertx(options).subscribe(
                vertx -> {
                    final DeploymentOptions deploymentOptions = readAndSetConfig(new DeploymentOptions());
                    for (String verticle : verticles) {
                        vertx.rxDeployVerticle(verticle, deploymentOptions)
                                .subscribe(result -> {
                                    LOGGER.log(Level.INFO, "Deployed verticle {0}", verticle);
                                }, e -> {
                                    LOGGER.log(Level.SEVERE, "Failed to deploy verticle " + verticle
                                            + " - " + e.getMessage(), e);
                                });
                    }
                },
                 t
                -> {
            LOGGER.log(Level.SEVERE, "Vert.x failed to start"
                    + " - " + t.getMessage(), t);
        }
        );
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
