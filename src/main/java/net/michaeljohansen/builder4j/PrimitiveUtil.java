package net.michaeljohansen.builder4j;

import java.io.Serializable;

class PrimitiveUtil {
  static Serializable getPrimitiveDefault(Class<?> primitiveClass) {
    if (primitiveClass.isAssignableFrom(int.class)) return 0;
    else if (primitiveClass.isAssignableFrom(long.class)) return 0L;
    else if (primitiveClass.isAssignableFrom(byte.class)) return (byte) 0;
    else if (primitiveClass.isAssignableFrom(boolean.class)) return false;
    else if (primitiveClass.isAssignableFrom(short.class)) return (short) 0;
    else if (primitiveClass.isAssignableFrom(double.class)) return 0.0d;
    else if (primitiveClass.isAssignableFrom(float.class)) return 0.0f;
    throw new IllegalArgumentException("Unknown primitive:" + primitiveClass.getName());
  }
}
