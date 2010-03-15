/* Copyright Applied Industrial Logic Limited 2002. All rights Reserved */
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
package com.ail.core.xmlbinding;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.exolab.castor.mapping.AbstractFieldHandler;
import org.exolab.castor.mapping.FieldDescriptor;
import org.exolab.castor.types.AnyNode;

import com.ail.core.XMLString;

/**
 * Castor data field handler which wraps a fields of type XMLString.<p/>
 * Typically, if an object contains a property of type {@link com.ail.core.XMLString XMLString}, you will want the
 * contents of that string to be embdedded into the XML generated by {@link com.ail.core.xmlbinding.ToXMLCommand ToXMLCommand}
 * as though it were an inherent node in the XML, and for {@link com.ail.core.xmlbinding.FromXMLCommand FromXMLCommand} to 
 * read the embedded nodes into the XMLString instance.<p/> 
 * This isn't the most natural behaviour for Castor, but it is achievable using this FieldHandler, and castor's AnyNode type.<p/>
 * Assuming that you have an object (com.core.MyObject) which contains an XMLString property <code>myBitOfXml</code> with the appropriate getters 
 * and setters, and the following castor mapping: <pre><small>
 *    ...
 *    &lt;class name="com.core.MyObject"&gt;
 *       &lt;field name="myBitOfXml" type="org.exolab.castor.types.AnyNode" handler="com.ail.core.xmlbinding.CastorXMLStringFieldHandler"&gt;
 *           &lt;bind-xml type="any" node="element"/&gt; 
 *       &lt;/field&gt;
 *   &lt;/class&gt;
 *   ...</small></pre>
 * The following section of code:<pre><small>
 *    MyObject mobj=new MyObj();
 *    mobj.setMyBitOfXml(new XMLString("&lt;name&gt;Mr. Pig&lt;/name&gt;"));
 *    System.out.println(core.toXml(mobj));</small></pre>
 *   
 * Would generate the following output:<pre><small>
 *   &lt;myObj xsi:type='java:com.ail.MyObj' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'&gt;
 *      &lt;myBitOfXml&gt;&lt;name&gt;Mr. Pig&lt;/name&gt;&lt;/myBitOfXml&gt;
 *   &lt;/myObj&gt;
 *</small></pre>
 */
public class CastorXMLStringFieldHandler extends AbstractFieldHandler {

    /**
     * uses reflection to retrieve the value then output it in it's raw format (i.e. as XML).
     */
    public Object getValue(Object object) throws IllegalStateException {
        FieldDescriptor f = getFieldDescriptor();
        String fieldName = f.getFieldName();
        XMLString value = null;
        
        try {
            value = (XMLString)PropertyUtils.getProperty(object, fieldName);
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        }
        
        if (value==null) {
            return "";
        }
        
        return value.toStringWithEntityReferences(true);
    }

    public Object newInstance(Object arg0) throws IllegalStateException {
        return new String();
    }

	public Object newInstance(Object arg0, Object arg1[]) throws IllegalStateException {
		return new String();
	}

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
		setValue(object, "");     
    }

    public void setValue(Object object, Object value) throws IllegalStateException, IllegalArgumentException {
		FieldDescriptor f = getFieldDescriptor();
		String fieldName = f.getFieldName();
		int startIdx=0;
        
        // get the XML out of the node's first child.
        AnyNode node=(AnyNode)value;
        String xml=node.getFirstChild().toString();

        // The xml variable may at this point include an XML header (in the 
        // form: <?xml version="1.0" encoding="UTF-8"?>). We don't want that header to end up 
        // in the XMLString, but I don't want to go to the expense of doing proper regex pattern
        // matching, so we're going to assume that the real XML starts at char 39. 
        if (xml.length()>39 && xml.charAt(39)=='<' && xml.charAt(36)=='?') {
            startIdx=39;
        }

        try {
            PropertyUtils.setProperty(object, fieldName, new XMLString(xml.substring(startIdx)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }     
    }
}
