package mihalyi.vertx.dataproducer;

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
    
    public static void longRunningTask() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(VertxHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deployVerticles(Vertx vertx, String... verticles) {
        final DeploymentOptions deploymentOptions = readAndSetConfig(new DeploymentOptions());
        for (String verticle : verticles ) {
            vertx.deployVerticle(verticle, deploymentOptions, deploymentResult -> {
                if (deploymentResult.failed()) {
                    LOGGER.log(Level.SEVERE, "Failed to deploy verticle " + verticle
                                    + " - " + deploymentResult.cause().getMessage(),
                                    deploymentResult.cause());
                } else {
                    LOGGER.log(Level.INFO, "Deployed verticle {0}", verticle);
                }
            });
        }
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
