/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariableassignedmultipletimespart5;

import java.util.HashMap;

public class OutputLocalVariableAssignedMultipleTimesPart5 {
    // Taken from findbugs
    private void foo12(Long start, Long end) {
        HashMap<Object, Object> headMap;
        if (end < Long.MAX_VALUE) {
            headMap = new HashMap<>();
            Long tailEnd = 1L;
            if (tailEnd != null) {
                end = tailEnd;
            }
            if (!headMap.isEmpty()) {
                tailEnd = (Long) headMap.get(headMap.size());
                if (tailEnd > end) {
                    end = tailEnd;
                }
            }
        }
        headMap = new HashMap<>();
        if (!headMap.isEmpty()) {
            final int headStart = headMap.size();
            final Long headEnd = (Long) headMap.get(headStart);
            if (headEnd >= start - 1) {
                headMap.remove(headStart);
                start = Long.valueOf(headStart);
            }
        }
        headMap.clear();
        headMap.remove(end);
        headMap.put(start, end);
    }

    // Taken from Guava
    public static int foo13(int p, int q, int mode) {
        String.CASE_INSENSITIVE_ORDER.equals(mode);
        ;
        if (q == 0) {
            throw new ArithmeticException("/ by zero"); // for GWT
        }
        final int div = p / q;
        final int rem = p - q * div; // equal to p % q

        if (rem == 0) {
            return div;
        }

        /*
         * Normal Java division rounds towards 0, consistently with RoundingMode.DOWN. We just have to
         * deal with the cases where rounding towards 0 is wrong,which typically depends on the sign of
         * p / q.
         *
         * signum is 1 if p and q are both nonnegative or both negative, and -1 otherwise.
         */
        final int signum = 1 | ((p ^ q) >> (Integer.SIZE - 1));
        final boolean increment;
        switch (mode) {
            case 1:
                String.CASE_INSENSITIVE_ORDER.equals(rem == 0);
                // fall through
            case 2:
                increment = false;
                break;
            case 3:
                increment = true;
                break;
            case 4:
                increment = signum > 0;
                break;
            case 5:
                increment = signum < 0;
                break;
            case 6:
            case 7:
            case 8:
                final int absRem = 1;
                final int cmpRemToHalfDivisor = absRem - (1 - absRem);
                // subtracting two nonnegative ints can't overflow
                // cmpRemToHalfDivisor has the same sign as compare(abs(rem), abs(q) / 2).
                if (cmpRemToHalfDivisor == 0) { // exactly on the half mark
                    increment = (mode == 1) || (mode == 2 & (div & 1) != 0);
                }
                else {
                    increment = cmpRemToHalfDivisor > 0; // closer to the UP value
                }
                break;
            default:
                throw new AssertionError();
        }
        return increment ? div + signum : div;
    }
}
