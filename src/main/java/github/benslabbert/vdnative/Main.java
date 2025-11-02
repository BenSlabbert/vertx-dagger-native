/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdnative;

import io.vertx.launcher.application.HookContext;
import io.vertx.launcher.application.VertxApplication;
import io.vertx.launcher.application.VertxApplicationHooks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends VertxApplication {

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    int code = new Main(args).launch();
    log.info("Launching code {}", code);
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
