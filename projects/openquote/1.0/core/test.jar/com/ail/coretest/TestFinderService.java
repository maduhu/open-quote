/* Copyright Applied Industrial Logic Limited 2002. All rights reserved. */
/*
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later 
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51 
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.ail.coretest;

import com.ail.core.Core;
import com.ail.core.VersionEffectiveDate;
import com.ail.core.Type;
import com.ail.core.configure.ConfigurationHandler;
import com.ail.core.configure.finder.GetClassListCommand;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import java.util.Iterator;

/**
 * @version $Revision: 1.2 $
 * @state $State: Exp $
 * @date $Date: 2005/07/16 10:23:24 $
 * @source $Source: /home/bob/CVSRepository/projects/core/test.jar/com/ail/coretest/TestFinderService.java,v $
 */
public class TestFinderService extends CoreUserTestCase {
    /**
     * Constructs a test case with the given name.
     */
    public TestFinderService(String name) {
        super(name);
    }

    public static Test suite() {
		return new TestSuite(TestFinderService.class);
    }

	public static void main(String[] args) {
		TestRunner.run(suite());
	}

    /**
     * Sets up the fixture (run before every test).
     * Get an instance of Core, and delete the testnamespace from the config table.
     */
    protected void setUp() {
        ConfigurationHandler.reset();
        setVersionEffectiveDate(new VersionEffectiveDate());
        tidyUpTestData();
        setCore(new Core(this));
        getCore().resetConfiguration();
        setVersionEffectiveDate(new VersionEffectiveDate());
        ConfigurationHandler.reset();
    }

    /**
     * Tears down the fixture (run after each test finishes)
     */
    protected void tearDown() {
		tidyUpTestData();
    }

    /**
     * The GetClassList service searches for class that implement specific interfaces (or
     * extend a base class). Given the class to search for and a portion of the package
     * structure to search under, it will return a list of all the class it finds that implement
     * of extend the class. This test performs a search under com.ail.core and checks that
     * a number of known classes are returned.
     * <ol>
     * <li>Create an instance of the GetClassList command object.</li>
     * <li>Set the command to search for instances of Type.class.</li>
     * <li>Set the command to search under com.ail.core in the package structure.</li>
     * <li>Invoke the command.</li>
     * <li>Check that the returned list includes:<ul>
     *   <li>com.ail.core.Version</li>
     *   <li>com.ail.core.History</li>
     *   <li>com.ail.core.configure.Builder</li>
     * </ul></li>
     * <li>Fail if any exceptions are thrown, or if any of the above are not returned.</li>
     * </ol>
     *
     * @throws Exception
     */
    public void testSimpleClassFinder() throws Exception {
        GetClassListCommand command = (GetClassListCommand)getCore().newCommand("GetClassList");
        command.setSearchClassArg(Type.class.getName());
        command.setSearchPackageArg("com.ail.core");
        command.invoke();
        assertTrue(command.getFoundImplementorsRet().contains("com.ail.core.Version"));
        assertTrue(command.getFoundImplementorsRet().contains("com.ail.core.History"));
        assertTrue(command.getFoundImplementorsRet().contains("com.ail.core.configure.Builder"));
    }

    /**
     * The class finder service should always return a list - even if it is empty.
     * This test asks the finder to find implementors of a class that doesn't exist.
     * <ol>
     * <li>Create an instance of the GetClassList command object</li>
     * <li>Set the command to search for an instance of "com.ail.ClassThatDoesNotExist"</li>
     * <li>Invoke the command</li>
     * <li>Fail if any exceptions are thrown</li>
     * <li>Fail if the class list returned is null, or not empty</li>
     * </ol>
     * @throws Exception
     */
    public void testClassFinderForMissingClass() throws Exception {
        GetClassListCommand command = (GetClassListCommand)getCore().newCommand("GetClassList");
        command.setSearchClassArg("com.ail.ClassThatDoesNotExist");
        command.invoke();
        assertNotNull(command.getFoundImplementorsRet());
        assertEquals(0, command.getFoundImplementorsRet().size());
    }

    /**
     * By default the class finder service searches under the com.ail package tree. This test
     * checks that class outside of this tree are ignored.
     * <ol>
     * <li>Create an instance of the GetClassList command object</li>
     * <li>Set the command to search for an instance of "java.io.Serializable"</li>
     * <li>Invoke the command</li>
     * <li>Fail if any exceptions are thrown</li>
     * <li>Fail if the class list returned is null, or empty</li>
     * <li>Fail if the class list returned includes any class outside of com.ail</li>
     * </ol>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void testForClassesOutsideComAil() throws Exception {
        GetClassListCommand command = (GetClassListCommand)getCore().newCommand("GetClassList");
        command.setSearchClassArg("java.io.Serializable");
        command.invoke();
        assertNotNull(command.getFoundImplementorsRet());
        assertTrue(command.getFoundImplementorsRet().size()>0);
        for(Iterator it=command.getFoundImplementorsRet().iterator() ; it.hasNext() ; ) {
            assertTrue(((String)it.next()).startsWith("com.ail"));
        }
    }

    /**
     * The class finder service can searches under any specified package tree. This test
     * checks that class outside of a selected tree are ignored.
     * <ol>
     * <li>Create an instance of the GetClassList command object</li>
     * <li>Set the command to search for an instance of "java.lang.Exception"</li>
     * <li>Set the search package to "java.io"</li>
     * <li>Invoke the command</li>
     * <li>Fail if any exceptions are thrown</li>
     * <li>Fail if the class list returned is null, or empty</li>
     * <li>Fail if the classes list returned includes any class outside of java.io</li>
     * </ol>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void testForClassesOutsideSpecifiedPackage() throws Exception {
        GetClassListCommand command = (GetClassListCommand)getCore().newCommand("GetClassList");
        command.setSearchClassArg("java.lang.Exception");
        command.setSearchPackageArg("com.ail.core.configure");
        command.invoke();
        assertNotNull(command.getFoundImplementorsRet());
        assertTrue(command.getFoundImplementorsRet().size()>0);
        for(Iterator it=command.getFoundImplementorsRet().iterator() ; it.hasNext() ; ) {
            assertTrue(((String)it.next()).startsWith("com.ail.core.configure"));
        }
    }
}
