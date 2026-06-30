/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.withprefixwhitespaces;

/**
 * Contains ungrouped constructors with irregular leading whitespaces.
 * Verifies that leading whitespaces are carried along.
 */
public class InputWithPrefixWhitespaces {

    int x;
InputWithPrefixWhitespaces() {}

    InputWithPrefixWhitespaces(String s) {}

            void foo() {}

        InputWithPrefixWhitespaces(int x) {}
    // violation above 'Constructors should be grouped together.*'


    InputWithPrefixWhitespaces(String s, int x) {}
    // violation above 'Constructors should be grouped together.*'
}
