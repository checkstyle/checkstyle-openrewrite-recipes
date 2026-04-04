/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.singlelinecomment;

    // violation below 'Annotation 'Deprecated' should be on the same line with its target.'
    @Deprecated // some comment
    class InputSingleLineComment {
    }
