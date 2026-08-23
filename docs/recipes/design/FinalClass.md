# FinalClass Recipe

The `FinalClass` recipe ensures that a class which has only private constructors is declared as `final`, since it cannot be extended by other classes.

For more detailed information on this rule, please refer to the official [Checkstyle FinalClass Documentation](https://checkstyle.org/checks/design/finalclass.html).

## Usage

To configure this Checkstyle rule, add the following module to your Checkstyle configuration file (`checkstyle.xml`):

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalClass"/>
  </module>
</module>
```

## Example

Below is an example showing how the `FinalClass` recipe transforms your code.

### Input

The following class has only a private constructor, but is not marked as final.

```java
public class UtilityClass {
    private UtilityClass() {
        // hidden constructor
    }

    public static void doSomething() {
        // ...
    }
}
```

### Output

After applying the recipe, the class is correctly marked as final.

```java
public final class UtilityClass {
    private UtilityClass() {
        // hidden constructor
    }

    public static void doSomething() {
        // ...
    }
}
```

### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
--- a/UtilityClass.java
+++ b/UtilityClass.java
@@ -1,7 +1,7 @@
-public class UtilityClass {
+public final class UtilityClass {
     private UtilityClass() {
         // hidden constructor
     }
 
     public static void doSomething() {
         // ...
     }
 }
```
