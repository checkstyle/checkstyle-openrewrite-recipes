# EmptyForIteratorPad Recipe

The `EmptyForIteratorPad` recipe fixes Checkstyle EmptyForIteratorPad violations by correcting padding around empty for-loop iterators.

For more detailed information on this rule, please refer to the official [Checkstyle EmptyForIteratorPad Documentation](https://checkstyle.org/checks/whitespace/emptyforiteratorpad.html).

## Usage

To configure this Checkstyle rule, add the following module to your Checkstyle configuration file (`checkstyle.xml`):

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyForIteratorPad"/>
  </module>
</module>
```

### Options

| Property | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `option` | `string` | `"nospace"` | Policy on how to pad an empty for-loop iterator. Supported values are: `nospace` (no space is allowed after the semicolon/before the closing parenthesis) and `space` (a space is required after the semicolon/before the closing parenthesis). |

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyForIteratorPad">
      <property name="option" value="space"/>
    </module>
  </module>
</module>
```

## Examples

Below are examples showing how the `EmptyForIteratorPad` recipe transforms your code.

### Using the `nospace` Option (Default)

#### Input

In the following loop, there is a space before the closing parenthesis when the iterator is empty.

```java
public class ExampleClass {
    public void test() {
        for (int i = 0; i < 10; ) {
        }
    }
}
```

#### Output

After applying the recipe, the space is removed.

```java
public class ExampleClass {
    public void test() {
        for (int i = 0; i < 10;) {
        }
    }
}
```

#### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
@@ -3,3 +3,3 @@
     public void test() {
-        for (int i = 0; i < 10; ) {
+        for (int i = 0; i < 10;) {
         }
```

### Using the `space` Option

#### Input

In the following loop, there is no space before the closing parenthesis when the iterator is empty.

```java
public class ExampleClass {
    public void test() {
        for (int i = 0; i < 10;) {
        }
    }
}
```

#### Output

After applying the recipe, a space is added.

```java
public class ExampleClass {
    public void test() {
        for (int i = 0; i < 10; ) {
        }
    }
}
```

#### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
@@ -3,3 +3,3 @@
     public void test() {
-        for (int i = 0; i < 10;) {
+        for (int i = 0; i < 10; ) {
         }
```
