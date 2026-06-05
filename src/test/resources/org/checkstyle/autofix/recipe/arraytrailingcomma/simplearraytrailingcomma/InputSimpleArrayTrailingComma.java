/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ArrayTrailingCommaCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.arraytrailingcomma.simplearraytrailingcomma;

public class InputSimpleArrayTrailingComma {

    int[] singleElementMultiLine = new int[] {
        1 // violation 'Array should contain trailing comma.'
    };

    int[] multiElementMultiLine = new int[] {
        1,
        2,
        3 // violation 'Array should contain trailing comma.'
    };

    String[] stringArray = new String[] {
        "hello",
        "world" // violation 'Array should contain trailing comma.'
    };

    int[] alreadyHasComma = new int[] {
        1,
        2,
        3,
    };

    int[] singleLine = new int[] { 1, 2, 3 };

    int[] emptyArray = new int[] {};

    int[] sizedArray = new int[5];

    int[][] nestedArray = new int[][] {
        { // violation 'Array should contain trailing comma.'
            1 // violation 'Array should contain trailing comma.'
        }
    };
}
