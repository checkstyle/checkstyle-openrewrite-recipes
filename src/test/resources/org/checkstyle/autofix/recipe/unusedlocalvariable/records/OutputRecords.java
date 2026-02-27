/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.records;

public class OutputRecords {

    record MyRecord(int x) {
        MyRecord {
        }

        public void test() {
        }
    }

    public void testRecordLocal() {
        record LocalRecord(int y) {
            void method() {
            }
        }
        LocalRecord lr = new LocalRecord(1);
        lr.method();
    }
}
