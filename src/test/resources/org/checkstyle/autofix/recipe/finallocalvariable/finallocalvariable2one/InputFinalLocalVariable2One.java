/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
      <property name="id" value="strict"/>
      <property name="tokens" value="PARAMETER_DEF"/>
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.finallocalvariable2one;

public class InputFinalLocalVariable2One {
    private int m_ClassVariable = 0;
    //static block
    static
    {
        int i, j = 0;
        Runnable runnable = new Runnable()
        {
            public void run()
            {
            }
        };
    }
    public void FinalLocalVariable2One()
    {
        int i = 0;
        // final variable
        final int j = 2;

        int z;

        Object obj = new Object();

        int k = 0;

        String x = obj.toString();

        k++;

        k = 2;

        Runnable runnable = new Runnable()
        {
            public void run()
            {
                int q = 0;
            }
        };
    }

    // violation below "Variable 'aArg' should be declared final"
    public void method(int aArg, final int aFinal, int aArg2)
    {
        int z = 0;

        z++;

        aArg2++;
    }

    public void aMethod()
    {
        int i = 0;

        final int j = 2;

        int z;

        Object obj = new Object();

        int k = 0;

        String x = obj.toString();

        k++;

        final class Inner
        {
            public Inner()
            {
                int w = 0;
                Runnable runnable = new Runnable()
                {
                    public void run()
                    {
                    }
                };
            }
        }
    }
}
