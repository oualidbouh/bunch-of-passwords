package io.vertx.bunch.of.passwords;

import io.vertx.bunch.of.passwords.verticles.KeyPassVerticle;

public class BunchOfPasswords {
  public static void main(String[] argv) {
    io.vertx.core.Launcher.executeCommand("run", KeyPassVerticle.class.getName());
  }
}
