/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.records;

public class InputRecords {

    record MyRecord(int x) {
        MyRecord {
            int a = 12; // violation 'Unused named local variable'
            a = x;
        }

        public void test() {
            int b = 13; // violation 'Unused named local variable'
            b = x;
        }
    }

    public void testRecordLocal() {
        record LocalRecord(int y) {
            void method() {
                int c = 14; // violation 'Unused named local variable'
                c = y;
            }
        }
        LocalRecord lr = new LocalRecord(1);
        lr.method();
    }
}
