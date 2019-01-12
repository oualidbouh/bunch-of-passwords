package io.vertx.bunch.of.passwords;

import io.vertx.bunch.of.passwords.verticles.MainVerticle;

public class BunchOfPasswords {
  public static void main(String[] argv) {
    io.vertx.core.Launcher.executeCommand("run", MainVerticle.class.getName());
  }
}
