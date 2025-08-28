/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.finallocalvariable.localvariableone;

public class OutputLocalVariableOne {
    private int m_ClassVariable = 0;
    //static block
    static
    {
        final int i, j = 0;
        final Runnable runnable = new Runnable()
        {
            public void run()
            {
            }
        };
    }
    /** constructor */
    public void InputVariableOne()
    {
        final int i = 0;
        // final variable
        final int j = 2;

        final int z;

        final Object obj = new Object();

        int k = 0;

        final String x = obj.toString();

        k++;

        k = 2;
        final Runnable runnable = new Runnable()
        {
            public void run()
            {
                final int q = 0;
            }
        };
    }

    public void method(int aArg, final int aFinal, int aArg2)
    {
        int z = 0;

        z++;

        aArg2++;
    }

    public void aMethod()
    {
        final int i = 0;

        final int j = 2;

        final int z;

        final Object obj = new Object();

        int k = 0;

        final String x = obj.toString();

        k++;

        final class Inner
        {
            public Inner()
            {
                final int w = 0;
                final Runnable runnable = new Runnable()
                {
                    public void run()
                    {
                    }
                };
            }
        }
    }
}
