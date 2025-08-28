/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariabletwo;

public class InputLocalVariableTwo {
    public void anotherMethod()
    {
        boolean aBool = true;
        for (int i = 0, j = 1, k = 1; j < 10 ; j++)
        {
            k++;
            aBool = false;
        }

        int l = 0;
        {
            int weird = 0; // violation, "Variable 'weird' should be declared final"
            int j = 0; // violation, "Variable 'j' should be declared final"
            int k = 0; // violation, "Variable 'k' should be declared final"
            {
                l++;
            }
        }

        int weird = 0;
        weird++;

    }

    class InnerClass
    {
        private int mInner = 0;

        public int mInner2 = 0;
    }
}
