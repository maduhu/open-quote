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
package com.ail.openquote.motorplus;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ail.core.CoreProxy;
import com.ail.core.CoreUserBaseCase;
import com.ail.core.XMLString;
import com.ail.insurance.pageflow.Label;
import com.ail.insurance.pageflow.PageFlow;
import com.ail.insurance.pageflow.SectionScroller;
import com.ail.insurance.pageflow.util.Binding;

@SuppressWarnings("deprecation")
public class TestPageFlow extends CoreUserBaseCase {
    private static final long serialVersionUID = 0L;

    /**
     * Sets up the fixture (run before every test). Get an instance of Core, and delete the testnamespace from the config table.
     */
    @Before
    public void setUp() throws Exception {
        super.setupSystemProperties();
    }
    
    /**
	 * Test to ensure that the principal types used by the motor plus product can 
	 * be instantiated without throwing exceptions.
	 * @throws Exception
	 */
    @Test
	public void testInstantiateTypes() throws Exception {
		CoreProxy cp=new CoreProxy();
		
		cp.newProductType("AIL.Demo.MotorPlus", "Policy");
		cp.newProductType("AIL.Demo.MotorPlus", "VehicleAsset");
		cp.newProductType("AIL.Demo.MotorPlus", "DriverAsset");
		cp.newProductType("AIL.Demo.MotorPlus", "DriverHistoryAsset");
		cp.newProductType("AIL.Demo.MotorPlus", "QuotationPageFlow");
	}
    
    @Test
    public void testPageFlowStructure() throws Exception {
        CoreProxy cp=new CoreProxy();
        
        PageFlow pf=(PageFlow)cp.newProductType("AIL.Demo.MotorPlus", "QuotationPageFlow");
        System.out.println(cp.toXML(pf));
        
    }
    
    @Test
	public void testDataElementsOnLabel() throws Exception {
        CoreProxy cp=new CoreProxy();
        
        SectionScroller qs=new SectionScroller();
        
        Label l=new Label();
        l.setFormat("Hello %s");
        l.getParameter().add(new Binding("attribute[id='a']/value"));
        l.getParameter().add(new Binding("attribute[id='b']/value"));
        
        qs.setSectionTitle(l);
        
        System.out.println(""+cp.toXML(qs));

        String s="<sectionScroller minRows='0' maxRows='-1' addAndDeleteEnabled='true' xsi:type='java:com.ail.openquote.ui.SectionScroller' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>"+
                    "<sectionTitle format=\"Details for %s %s %s\">"+
                        "<parameter xpath=\"attribute[id='make']/value\"/>"+
                        "<parameter xpath=\"attribute[id='model']/value\"/>"+
                        "<parameter xpath=\"attribute[id='registration']/value\"/>"+
                    "</sectionTitle>"+
                 "</sectionScroller>";
        
        qs=(SectionScroller)cp.fromXML(SectionScroller.class, new XMLString(s));
        assertEquals(3, qs.getSectionTitle().getParameter().size());
        assertEquals("attribute[id='make']/value", qs.getSectionTitle().getParameter().get(0).getXpath());
    }
}
