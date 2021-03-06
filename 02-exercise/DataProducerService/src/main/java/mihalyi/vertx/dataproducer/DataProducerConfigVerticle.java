package mihalyi.vertx.dataproducer;

import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Verticle generating "fake" quotes based on the configuration.
 */
public class DataProducerConfigVerticle extends AbstractVerticle {

  /**
   * The address on which the data are sent.
   */
  public static final String ADDRESS = "market";
  
  /**
   * This method is called when the verticle is deployed.
   */
  @Override
  public void start() {
    // Read the configuration, and deploy a MarketDataVerticle for each company listed in the configuration.
    JsonArray quotes = config().getJsonArray("companies");
    for (Object q : quotes) {
      JsonObject company = (JsonObject) q;
    // Deploy another verticle without configuration.  
      vertx.deployVerticle(MarketDataVerticle.class.getName(), new DeploymentOptions().setConfig(company));
    }

    
    // Úloha 1 - Deploy verticle RestQuoteAPIVerticle s tou istou konfiguráciou ako má tento verticle DataProducerConfigVerticle
    // Pozrite konfiguráciu uloženú v súbore src/conf/config.json
    vertx.deployVerticle

  }
}
