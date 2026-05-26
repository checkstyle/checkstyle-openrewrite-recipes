/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariableassignedmultipletimespart6;

import java.util.Locale;

public class OutputLocalVariableAssignedMultipleTimesPart6 {
    public String foo11(final Object o, boolean getMinimal) {

        String n = System.lineSeparator();
        if (n != null) {
            return n;
        }

        try {

            String className = "";
            int kind = 0;
            boolean isParameterToInitMethodofAnonymousInnerClass = false;
            boolean isSyntheticMethod = false;
            String m = "";

            if (!m.equals("") && !"<init>".equals(m.toLowerCase(Locale.getDefault()))) {
                final String c = "className";
                // get inherited annotation
                String inheritedAnnotations = new String();
                if (c.charAt(1) > 0) {

                    n = "";
                    if (n != null) {
                        inheritedAnnotations += "";
                    }
                }
                for (int i = 5; i < 10; i++) {
                    n = new String("");
                    if (n != null) {
                        inheritedAnnotations += "";
                    }
                }
                if (n == null) {
                    String.CASE_INSENSITIVE_ORDER.equals("#" + inheritedAnnotations.length());
                }
                if (!inheritedAnnotations.isEmpty()) {
                    if (inheritedAnnotations.length() == 1) {
                        return inheritedAnnotations;
                    }
                    if (!getMinimal) {
                        return inheritedAnnotations;
                    }

                    String min = inheritedAnnotations;
                    if (min.length() == 0) {
                        inheritedAnnotations = null;
                        min = inheritedAnnotations;
                    }
                    return min;
                }
                // check to see if method is defined in this class;
                // if not, on't consider default annotations
                if (inheritedAnnotations == null) {
                    return null;
                }
                if (inheritedAnnotations.equals("")) {
                    String.CASE_INSENSITIVE_ORDER.equals("l" + " defines " + m);
                }
            } // if not static

            className = "reassigned";
            isParameterToInitMethodofAnonymousInnerClass = true;
            isSyntheticMethod = true;
            kind = 1;
            m = "reassigned";

            return n;
        }
        catch (Exception e) {
            String.CASE_INSENSITIVE_ORDER.equals(e);
            ;
            return null;
        }

    }
}
