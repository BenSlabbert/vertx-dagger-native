/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdnative;

import java.util.List;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.build.Plugin;
import net.bytebuddy.description.method.MethodDescription.InDefinedShape;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.matcher.ElementMatcher;

public class TransformerPlugin implements Plugin {

  @Override
  public Builder<?> apply(Builder<?> builder, TypeDescription target, ClassFileLocator clf) {
    List<InDefinedShape> advisedMethods =
        target.getDeclaredMethods().stream()
            .filter(s -> s.getDeclaredAnnotations().isAnnotationPresent(EnhanceMe.class))
            .toList();

    if (advisedMethods.isEmpty()) {
      System.err.println("no annotations found for EnhanceMe method");
      return builder;
    }

    for (InDefinedShape tm : advisedMethods) {
      System.err.println("enhancing method " + tm);
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
    return matcher().matches(typeDefinitions);
  }

  ElementMatcher<TypeDescription> matcher() {
    return target ->
        target.getDeclaredMethods().stream()
            .anyMatch(s -> s.getDeclaredAnnotations().isAnnotationPresent(EnhanceMe.class));
  }
}
