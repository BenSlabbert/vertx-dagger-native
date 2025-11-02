/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdnative;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.build.Plugin;
import net.bytebuddy.description.method.MethodDescription.InDefinedShape;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType.Builder;

public class TransformerPlugin implements Plugin {

  @Override
  public Builder<?> apply(Builder<?> builder, TypeDescription target, ClassFileLocator clf) {
    for (InDefinedShape tm : target.getDeclaredMethods()) {
      builder = builder.method(md -> md.equals(tm)).intercept(Advice.to(EnhanceAdvice.class));
    }

    return builder;
  }

  @Override
  public void close() {
    // nothing open
  }

  @Override
  public boolean matches(TypeDescription typeDefinitions) {
    return typeDefinitions.getDeclaredMethods().stream()
        .anyMatch(s -> s.getDeclaredAnnotations().isAnnotationPresent(EnhanceMe.class));
  }
}
