package net.michaeljohansen.builder4j;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.function.Function;

public class Builder<Target> {
  public static <Target> Builder<Target> builderFor(Class<Target> targetClass) {
    return new Builder<>(targetClass);
  }

  private final Target target;
  private final AssignableInvocationHandler assignableInvocationHandler = new AssignableInvocationHandler();

  private final Object[] lock = new Object[0];

  public Builder(Class<Target> targetClass) {
    try {
      Class<? extends Target> proxyClass = ProxyFactory.createProxyClass(
        targetClass,
        assignableInvocationHandler
      );
      // Constructors are optional
      this.target = targetClass.cast(
        getUnsafe().allocateInstance(proxyClass)
      );
    } catch (Exception e) {
      throw new IllegalStateException(
        "Failed to create instance of class " + targetClass.getName(),
        e
      );
    }
  }


  public <Property> Builder<Target> with(
    Function<Target, Property> accessor,
    Property property
  ) {
    synchronized (lock) {
      accessor.apply(target);
      assignableInvocationHandler.set(property);
      return this;
    }
  }

  public Target build() {
    return target;
  }

  private Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
    theUnsafe.setAccessible(true);
    return (Unsafe) theUnsafe.get(null);
  }

}
