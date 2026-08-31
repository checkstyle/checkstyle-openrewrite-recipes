# ConstructorsDeclarationGrouping Recipe

The `ConstructorsDeclarationGrouping` recipe fixes Checkstyle ConstructorsDeclarationGrouping violations by grouping all constructors together in a class and optionally ordering them by increasing parameter count.

For more detailed information on this rule, please refer to the official [Checkstyle ConstructorsDeclarationGrouping Documentation](https://checkstyle.org/checks/coding/constructorsdeclarationgrouping.html).

## Usage

To configure this Checkstyle rule, add the following module to your Checkstyle configuration file (`checkstyle.xml`):

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ConstructorsDeclarationGrouping"/>
  </module>
</module>
```

### Options

| Property | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `orderByIncreasingParameterCount` | `boolean` | `false` | When set to `true`, constructors will also be ordered by increasing parameter count. |

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ConstructorsDeclarationGrouping">
      <property name="orderByIncreasingParameterCount" value="true"/>
    </module>
  </module>
</module>
```

## Examples

Below are examples showing how the `ConstructorsDeclarationGrouping` recipe transforms your code.

### Grouping Constructors Together

#### Input

In the following class, the constructors are separated by a helper method.

```java
public class ExampleClass {
    public ExampleClass() {}

    public void helper() {}

    public ExampleClass(int x) {}
}
```

#### Output

After applying the recipe, the constructors are grouped together.

```java
public class ExampleClass {
    public ExampleClass() {}

    public ExampleClass(int x) {}

    public void helper() {}
}
```

#### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
@@ -3,3 +3,3 @@
     public ExampleClass() {}
-
-    public void helper() {}
 
     public ExampleClass(int x) {}
+
+    public void helper() {}
 }
```

### Ordering by Parameter Count

With `orderByIncreasingParameterCount` set to `true`, the constructors are sorted.

#### Input

```java
public class ExampleClass {
    public ExampleClass(String s) {}

    public ExampleClass() {}
}
```

#### Output

```java
public class ExampleClass {
    public ExampleClass() {}

    public ExampleClass(String s) {}
}
```

#### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
@@ -2,3 +2,3 @@
 public class ExampleClass {
-    public ExampleClass(String s) {}
-
     public ExampleClass() {}
+
+    public ExampleClass(String s) {}
 }
```
