# AvoidNoArgumentSuperConstructorCall Recipe

The `AvoidNoArgumentSuperConstructorCall` recipe fixes Checkstyle AvoidNoArgumentSuperConstructorCall violations by removing unnecessary no-argument super constructor calls (`super()`).

For more detailed information on this rule, please refer to the official [Checkstyle AvoidNoArgumentSuperConstructorCall Documentation](https://checkstyle.org/checks/coding/avoidnoargumentsuperconstructorcall.html).

## Usage

To configure this Checkstyle rule, add the following module to your Checkstyle configuration file (`checkstyle.xml`):

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidNoArgumentSuperConstructorCall"/>
  </module>
</module>
```

## Example

Below is an example showing how the `AvoidNoArgumentSuperConstructorCall` recipe transforms your code.

### Input

The following constructor explicitly calls `super()` which is unnecessary as it is called implicitly by Java.

```java
public class ExampleClass extends ArrayList<Object> {
    public ExampleClass() {
        super();
    }
}
```

### Output

After applying the recipe, the redundant `super()` call is removed.

```java
public class ExampleClass extends ArrayList<Object> {
    public ExampleClass() {
    }
}
```

### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
--- a/ExampleClass.java
+++ b/ExampleClass.java
@@ -2,3 +2,2 @@
     public ExampleClass() {
-        super();
     }
```
