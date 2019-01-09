package io.vertx.bunch.of.passwords;

public class BunchOfPasswords {
  public static void main(String[] argv) {
    io.vertx.core.Launcher.executeCommand("run", MainVerticle.class.getName());
  }
}
