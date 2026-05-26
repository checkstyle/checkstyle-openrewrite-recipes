/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariableassignedmultipletimespart4;

public class InputLocalVariableAssignedMultipleTimesPart4 {
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

            if (o instanceof String) {

                className = ((String) o).getClass().getName();
                kind = 3;
            }
            else if (o instanceof String) {
                assert false;
                className = (String) o;
                kind = 4;
            }
            else {
                throw new IllegalArgumentException("Can't" + o.getClass().getName());
            }

            // <init> method parameters for inner classes don't inherit default
            // annotations
            // since some of them are synthetic
            if (isParameterToInitMethodofAnonymousInnerClass) {
                return null;
            }

            // synthetic elements should not inherit default annotations
            if (isSyntheticMethod) {
                return null;
            }
            try {
                final String c = new String(className);

                if (c != null && c.equals("")) {
                    return null;
                }
            }
            catch (Exception e) {
                assert true;
            }

            // look for default annotation
            n = new String(className);
            if (n == null) {
                String.CASE_INSENSITIVE_ORDER.equals("Default annotation for " + kind + " is " + n);
            }
            if (n != null) {
                return n;
            }

            n = new String(className);
            if (n == null) {
                String.CASE_INSENSITIVE_ORDER.equals("Default annotation for any is " + n);
            }
            if (n != null) {
                return n;
            }

            final int p = className.lastIndexOf('.');
            className = className.substring(0, p + 1) + "package-info";
            n = new String(className);
            if (n == null) {
                String.CASE_INSENSITIVE_ORDER.equals("Default annotation for " + kind + " is " + n);
            }
            if (n != null) {
                return n;
            }

            n = new String(className);
            if (n == null) {
                String.CASE_INSENSITIVE_ORDER.equals("Default annotation for any is " + n);
            }
            if (n != null) {
                return n;
            }

            isParameterToInitMethodofAnonymousInnerClass = true;
            isSyntheticMethod = true;

            return n;
        }
        catch (Exception e) {
            String.CASE_INSENSITIVE_ORDER.equals(e);
            ;
            return null;
        }

    }
}
