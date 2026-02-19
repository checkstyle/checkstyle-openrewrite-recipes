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

public class OutputOverrideCaseFive {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
