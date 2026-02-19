/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionXpathSingleFilter">
    <property name="checks" value="MissingOverride"/>
    <property name="query" value="//METHOD_DEF[./IDENT[@text='toString']]"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingoverride.overridecasefive;

public class InputOverrideCaseFive {

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj) {// violation 'include.*@java.lang.Override.*when.*'@inheritDoc''
        return super.equals(obj);
    }
}
