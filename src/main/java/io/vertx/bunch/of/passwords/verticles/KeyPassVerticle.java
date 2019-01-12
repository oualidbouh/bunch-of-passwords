package io.vertx.bunch.of.passwords.verticles;


import io.vertx.bunch.of.passwords.config.Config;
import io.vertx.bunch.of.passwords.domain.Key;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;

public class KeyPassVerticle extends AbstractVerticle {

  Logger logger = LoggerFactory.getLogger(KeyPassVerticle.class);
  MongoClient mongoClient;

  @Override
  public void start(Future<Void> startFuture) {

    mongoClient = MongoClient.createShared(vertx, Config.mongoDbConfig());

    vertx.eventBus().consumer("key-pass", message -> {
      switch (message.body().toString()) {

        case "getKeys":

          mongoClient.find("keys", new JsonObject(), mongoHandler -> {
            if (mongoHandler.succeeded()) {
              message.reply(Json.encodePrettily(mongoHandler.result()));
            } else {
              message.reply("NO_KEY_FOUND");
            }
          });

          break;

        case "postKey":

          Key key = new Key();
          key.setLogin(message.headers().get("login"));
          key.setPassword(message.headers().get("password"));
          key.setLink(message.headers().get("link"));
          key.setLastEdit(message.headers().get("lastEdit"));

          logger.info("saving key = {}", key);

          mongoClient.save("keys", JsonObject.mapFrom(key), mongoHandler -> {
            if (mongoHandler.succeeded()) {
              logger.info("saving key = {}", key);
              message.reply(Json.encodePrettily(mongoHandler.result()));
            } else {
              logger.error("can't add document keypass = {}", key);
              message.reply("can't add document");
            }
          });

          break;

        default:
          message.reply("message handler not found");
          break;
      }
    });
  }
}
