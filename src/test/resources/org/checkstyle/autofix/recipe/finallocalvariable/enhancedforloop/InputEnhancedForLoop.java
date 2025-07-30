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

package org.checkstyle.autofix.recipe.finallocalvariable.enhancedforloop;

public class InputEnhancedForLoop {
    public void method1()
    {
        final java.util.List<Object> list = new java.util.ArrayList<>();

        for(Object a : list){    // violation, "should be declared final"
        }
    }

    public void method2()
    {
        final int[] squares = {0, 1, 4, 9, 16, 25};
        int x;                      // violation, "should be declared final"
        for (int i : squares) {     // violation, "should be declared final"
        }

    }
    // violation below, "should be declared final"
    public java.util.List<String> method3(java.util.List<String> snippets) {
        // violation below, "should be declared final"
        java.util.List<String> filteredSnippets = new java.util.ArrayList<>();
        for (String snippet : snippets) {     // violation, "should be declared final"
            filteredSnippets.add(snippet);
        }
        if (filteredSnippets.size() == 0) {
            String snippet = snippets.get(0);
            snippet = new String(snippet);
            filteredSnippets.add(snippet);
        }
        return filteredSnippets;
    }
}
