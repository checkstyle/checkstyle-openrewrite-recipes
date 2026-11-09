# OneStatementPerLine Recipe

The `OneStatementPerLine` recipe fixes Checkstyle violations where multiple
Java statements share a line by placing each statement on its own line.

For more information, see the official
[Checkstyle OneStatementPerLine documentation](https://checkstyle.org/checks/coding/onestatementperline.html).

## Usage

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OneStatementPerLine"/>
  </module>
</module>
```

## Example

### Input

```java
void initialize() {
    int first = 1; int second = 2;
}
```

### Output

```java
void initialize() {
    int first = 1;
    int second = 2;
}
```
