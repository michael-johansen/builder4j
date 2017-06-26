package net.michaeljohansen.builder4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static net.michaeljohansen.builder4j.PrimitiveUtil.getPrimitiveDefault;

class AssignableInvocationHandler implements InvocationHandler {

  private final Map<Method, Object> map = new HashMap<>();
  private Method lastMethodCalled;

  @Override
  public Object invoke(
    Object proxy,
    Method method,
    Object[] args
  ) throws Throwable {
    this.lastMethodCalled = method;
    if (method.getReturnType().isPrimitive() && !map.containsKey(method)) {
      return getPrimitiveDefault(method.getReturnType());
    }
    return map.get(method);
  }

  public <Property> void set(Property property) {
    map.put(lastMethodCalled, property);
  }

}
