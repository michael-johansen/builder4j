package net.michaeljohansen.builder4j;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationHandler;

class ProxyFactory {
  static <Target> Class<? extends Target> createProxyClass(
    Class<Target> targetClass,
    InvocationHandler mapInvocationHandler
  ) {
    return new ByteBuddy()
      .subclass(targetClass)
      .method(ElementMatchers.any())
      .intercept(InvocationHandlerAdapter.of(mapInvocationHandler))
      .make()
      .load(targetClass.getClassLoader())
      .getLoaded();
  }
}
