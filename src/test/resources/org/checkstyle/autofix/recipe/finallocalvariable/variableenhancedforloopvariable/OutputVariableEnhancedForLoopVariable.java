/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
      <property name="validateEnhancedForLoopVariable" value="true"/>
      <property name="tokens" value="VARIABLE_DEF, PARAMETER_DEF"/>
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.variableenhancedforloopvariable;

public class OutputVariableEnhancedForLoopVariable {
    public void method1()
    {
        final java.util.List<Object> list = new java.util.ArrayList<>();

        for(final Object a : list){
        }
    }

    public void method2()
    {
        final int[] squares = {0, 1, 4, 9, 16, 25};
        final int x;
        for (final int i : squares) {
        }

    }
    public java.util.List<String> method3(final java.util.List<String> snippets) {
        final java.util.List<String> filteredSnippets = new java.util.ArrayList<>();
        for (final String snippet : snippets) {
            filteredSnippets.add(snippet);
        }
        if (filteredSnippets.size() == 0) {
            String snippet = snippets.get(0);
            snippet = new String(snippet);
            filteredSnippets.add(snippet);
        }
        return filteredSnippets;
    }

    public void method4()
    {
        final java.util.List<Object> list = new java.util.ArrayList<>();

        for(final Object a : list) {
        }

        final Object a;
        if (list.isEmpty())
        {
            a = new String("empty");
        }
        else
        {
            a = new String("not empty");
        }

        for(Object b : list) {
            b = new String("b");
        }
    }
}
