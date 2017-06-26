package net.michaeljohansen.builder4j;

import org.junit.Test;

import java.util.Objects;

import static net.michaeljohansen.builder4j.Builder.builderFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BuilderTest {
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

  @Test
  public void DataClassShouldNotBeConstructableWithDefaultValues() throws Exception {
    assertThatThrownBy(() -> new Bean(null, null))
      .isInstanceOf(NullPointerException.class);
  }

  public static class Bean {
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

  public static class NestedBean {
    private final int property;

    public NestedBean(int property) {
      this.property = property;
    }

    public int getProperty() {
      return property;
    }
  }
}
