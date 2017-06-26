# Flexible builders for java 8

```java
public class Test {
  @Test
  public void builderShouldCreateInstanceWithValues() throws Exception {
    Bean bean = builderFor(Bean.class)
      .with(Bean::getProperty, "some value")
      .with(
        Bean::getNestedProperty,
        builderFor(NestedBean.class)
          .with(NestedBean::getProperty, 123)
          .build()
      )
      .build();

    assertThat(bean.getProperty()).isEqualTo("some value");
    assertThat(bean.getNestedProperty().getProperty()).isEqualTo(123);
  }
}
```

where the beans are defined like:
 
```java
public class Bean {
  private final String property;
  private final NestedBean nestedProperty;

  public Bean(
    String property,
    NestedBean nestedProperty
  ) {
    Objects.requireNonNull(property, "property is required");
    Objects.requireNonNull(nestedProperty, "nestedProperty is required");
    this.property = property;
    this.nestedProperty = nestedProperty;
  }

  public String getProperty() {
    return property;
  }

  public NestedBean getNestedProperty() {
    return nestedProperty;
  }
}

public class NestedBean {
  private final int property;

  public NestedBean(int property) {
    this.property = property;
  }

  public int getProperty() {
    return property;
  }
}
```
