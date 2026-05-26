/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariableassignedmultipletimespart3;

import java.util.Locale;

public class OutputLocalVariableAssignedMultipleTimesPart3 {
    public String foo11(final Object o, boolean getMinimal) {
        String n = System.lineSeparator();
        if (n != null) {
            return n;
        }

        try {
            String className;
            final int kind;
            boolean isParameterToInitMethodofAnonymousInnerClass = false;
            boolean isSyntheticMethod = false;
            if (o instanceof String || o instanceof Integer) {

                final String m;
                if (o instanceof String) {
                    m = (String) o;
                    isSyntheticMethod = m.equals("");
                    kind = 1;
                    className = this.getClass().getName();
                }
                else if (o instanceof String) {
                    m = "";
                    // Don't
                    isSyntheticMethod = m.equals("");
                    className = this.getClass().getName();
                    kind = 2;
                    if ("<init>".equals(m.toLowerCase(Locale.getDefault()))) {
                        final int i = className.lastIndexOf('$');
                        if (i + 1 < className.length()) {
                            isParameterToInitMethodofAnonymousInnerClass = true;
                        }
                    }
                }
                else {
                    throw new IllegalStateException("impossible");
                }
            } // associated with method

            className = "reassigned";
            isParameterToInitMethodofAnonymousInnerClass = true;
            isSyntheticMethod = true;
            n = "reassigned";

            return n;
        }
        catch (Exception e) {
            String.CASE_INSENSITIVE_ORDER.equals(e);
            ;
            return null;
        }

    }
}
