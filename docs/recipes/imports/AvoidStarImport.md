# AvoidStarImport Recipe

The `AvoidStarImport` recipe fixes Checkstyle AvoidStarImport violations by expanding star imports (`import package.*;`) into individual imports for the types that are actually used.

For more detailed information on this rule, please refer to the official [Checkstyle AvoidStarImport Documentation](https://checkstyle.org/checks/imports/avoidstarimport.html).

## Usage

To configure this Checkstyle rule, add the following module to your Checkstyle configuration file (`checkstyle.xml`):

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStarImport"/>
  </module>
</module>
```

## Example

Below is an example showing how the `AvoidStarImport` recipe transforms your code.

### Input

The following class imports all classes from the `java.util` package using a star import, which violates the rule.

```java
import java.util.*;

public class ExampleClass {
    List<String> list = new ArrayList<>();
}
```

### Output

After applying the recipe, the star import is replaced with specific imports for `List` and `ArrayList`.

```java
import java.util.ArrayList;
import java.util.List;

public class ExampleClass {
    List<String> list = new ArrayList<>();
}
```

### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
--- a/ExampleClass.java
+++ b/ExampleClass.java
@@ -1,3 +1,4 @@
-import java.util.*;
+import java.util.ArrayList;
+import java.util.List;
 
 public class ExampleClass {
```
