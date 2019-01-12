package io.vertx.bunch.of.passwords.config;

import io.vertx.core.json.JsonObject;

public class Config {
  /*todo : Externalize to config.yml*/
  public static JsonObject mongoDbConfig() {
    return new JsonObject()
      .put("username", "oualid")
      .put("password", "vertx2018")
      .put("authSource", "admin")
      .put("db_name", "keypass");
  }
}
