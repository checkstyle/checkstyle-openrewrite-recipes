/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.redundantimport.complexcase;
import java.io.*;

import java.util.List;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.Arrays;

import javax.swing.JToolBar;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneLayout;
import javax.swing.BorderFactory;

import static java.io.File.listRoots;
import static javax.swing.WindowConstants.*;
import static java.io.File.createTempFile;
import static java.io.File.pathSeparator;
import static org.checkstyle.autofix.recipe.redundantimport.complexcase.InputComplexCase.myStaticMethod;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Label;
import java.util.Date;
import java.util.Calendar;
import java.util.BitSet;

import static java.lang.Math.PI;

class OutputComplexCase {
    public static void myStaticMethod() {}
}
