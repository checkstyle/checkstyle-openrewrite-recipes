/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.redundantimport.complexcase;

import org.checkstyle.autofix.recipe.redundantimport.complexcase.*; // violation 'Redundant import from the same package'
import java.io.*;
    import java.lang.*; // violation 'Redundant import from the java.lang package'

import java.util.List;
import java.util.List; // violation 'Duplicate import'
import java.util.Iterator;
import java.util.Enumeration;
import java.util.Arrays;

import javax.swing.JToolBar;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneLayout;
import javax.swing.BorderFactory;

import static java.io.File.listRoots;
import static javax.swing.WindowConstants.*;
import static javax.swing.WindowConstants.*; // violation 'Duplicate import'
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

class InputComplexCase {
    public static void myStaticMethod() {}
}
