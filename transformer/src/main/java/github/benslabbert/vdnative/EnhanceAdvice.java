/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdnative;

import net.bytebuddy.asm.Advice.OnMethodEnter;
import net.bytebuddy.asm.Advice.OnMethodExit;
import net.bytebuddy.asm.Advice.Origin;

final class EnhanceAdvice {

  private EnhanceAdvice() {}

  @OnMethodEnter
  static void onEnter(@Origin("#t\\##m") String methodName) {
    System.err.println("EnhanceAdvice.onEnter: "+ methodName);
  }

  @OnMethodExit
  static void onExit(@Origin("#t\\##m") String methodName) {
    System.err.println("EnhanceAdvice.onExit: "+ methodName);
  }
}
