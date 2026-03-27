/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.withprefixwhitespaces;

public class OutputWithPrefixWhitespaces {

    int x;
OutputWithPrefixWhitespaces() {}

    OutputWithPrefixWhitespaces(String s) {}

        OutputWithPrefixWhitespaces(int x) {}


    OutputWithPrefixWhitespaces(String s, int x) {}

            void foo() {}
}
