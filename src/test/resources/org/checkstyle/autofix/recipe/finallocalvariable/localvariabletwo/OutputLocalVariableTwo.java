/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariabletwo;

public class OutputLocalVariableTwo {
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
            final int weird = 0;
            final int j = 0;
            final int k = 0;
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
