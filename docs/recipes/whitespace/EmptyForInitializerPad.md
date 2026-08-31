# EmptyForInitializerPad Recipe

The `EmptyForInitializerPad` recipe fixes Checkstyle EmptyForInitializerPad violations by correcting padding around empty for-loop initializers.

For more detailed information on this rule, please refer to the official [Checkstyle EmptyForInitializerPad Documentation](https://checkstyle.org/checks/whitespace/emptyforinitializerpad.html).

## Usage

To configure this Checkstyle rule, add the following module to your Checkstyle configuration file (`checkstyle.xml`):

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyForInitializerPad"/>
  </module>
</module>
```

### Options

| Property | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `option` | `string` | `"nospace"` | Policy on how to pad an empty for-loop initializer. Supported values are: `nospace` (no space is allowed before the semicolon) and `space` (a space is required before the semicolon). |

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyForInitializerPad">
      <property name="option" value="space"/>
    </module>
  </module>
</module>
```

## Examples

Below are examples showing how the `EmptyForInitializerPad` recipe transforms your code.

### Using the `nospace` Option (Default)

#### Input

In the following loop, there is a space before the empty initializer's semicolon.

```java
public class ExampleClass {
    public void test() {
        for ( ; true; ) {
        }
    }
}
```

#### Output

After applying the recipe, the space is removed.

```java
public class ExampleClass {
    public void test() {
        for (; true; ) {
        }
    }
}
```

#### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
@@ -3,3 +3,3 @@
     public void test() {
-        for ( ; true; ) {
+        for (; true; ) {
         }
```

### Using the `space` Option

#### Input

In the following loop, there is no space before the empty initializer's semicolon.

```java
public class ExampleClass {
    public void test() {
        for (; ; ) {
            break;
        }
    }
}
```

#### Output

After applying the recipe, a space is added before the semicolon.

```java
public class ExampleClass {
    public void test() {
        for ( ; ; ) {
            break;
        }
    }
}
```

#### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
@@ -3,3 +3,3 @@
     public void test() {
-        for (; ; ) {
+        for ( ; ; ) {
             break;
```
