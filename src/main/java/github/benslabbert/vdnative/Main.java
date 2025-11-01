/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdnative;

import io.vertx.core.VertxException;
import io.vertx.launcher.application.HookContext;
import io.vertx.launcher.application.VertxApplication;
import io.vertx.launcher.application.VertxApplicationHooks;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends VertxApplication {

  static {
    try (var is =
        Thread.currentThread().getContextClassLoader().getResourceAsStream("logging.properties")) {
      LogManager.getLogManager().readConfiguration(is);
    } catch (IOException e) {
      throw VertxException.noStackTrace(e);
    }
  }

  private static final Logger log = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {

    int code = new Main(args).launch();
    log.log(Level.INFO, "Launching code {0}", code);
  }

  private Main(String[] args) {
    super(
        args,
        new VertxApplicationHooks() {
          @Override
          public void beforeDeployingVerticle(HookContext context) {
            context.deploymentOptions().setWorkerPoolSize(1).setWorkerPoolName("worker-pool");
          }

          @Override
          public void beforeStartingVertx(HookContext context) {
            context
                .vertxOptions()
                .setWorkerPoolSize(1)
                .setEventLoopPoolSize(1)
                .setInternalBlockingPoolSize(1);
          }

          @Override
          public void afterVertxStopped(HookContext context) {
            log.info("Vertx stopped");
          }
        });
  }
}
