/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedimports.unusedcaseone;

import java.io.*;
import java.lang.*;
import java.lang.String; // violation 'Unused import - java.lang.String.'

import java.util.List; // violation 'Unused import - java.util.List.'
import java.util.List; // violation 'Unused import - java.util.List.'
import java.lang.*;
import java.util.Iterator;
import java.util.Enumeration; // violation 'Unused import - java.util.Enumeration.'
import java.util.Arrays;
import javax.swing.JToolBar;
import javax.swing.JToggleButton; // violation 'Unused import - javax.swing.JToggleButton.'

import javax.swing.BorderFactory; // violation 'Unused import - javax.swing.BorderFactory.'

import static java.io.File.listRoots;

import static javax.swing.WindowConstants.*;
import static java.io.File. // violation 'Unused import - java.io.File.createTempFile.'
    createTempFile;

import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Label; // violation 'Unused import - java.awt.Label.'
import java.util.Date;
import java.util.Calendar;
import java.util.BitSet;

/**
 * Test case for imports
 * Here's an import used only by javadoc: {@link Date}.
 * @see Calendar Should avoid unused import for Calendar
 **/
public class InputUnusedCaseOne {

    private Class mUse1 = null;
    private Class mUse2 = java.io.File.class;
    private Class mUse3 = Iterator[].class;
    private Class mUse4 = java.util.Enumeration[].class;

    {
        int[] x = {};
        Arrays.sort(x);
        Object obj = javax.swing.BorderFactory.createEmptyBorder();
        File[] files = listRoots();
    }

    private JToolBar.Separator mSep = null;

    private Object mUse5 = new Object();

    private Object mUse6 = new javax.swing.JToggleButton.ToggleButtonModel();

    private int Component;

    /**
     * method comment with JavaDoc-only import {@link BitSet}
     */
    public void Label() {}

    /**
     * Renders to a {@linkplain Graphics2D graphics context}.
     * @throws HeadlessException if no graphis environment can be found.
     */
    public void render() {}

    public void aMethodWithManyLinks() {}
}
