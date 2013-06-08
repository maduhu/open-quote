package com.ail.pageflow.util;

import static com.ail.pageflow.util.AddProductNameToPageflowContextService.PRODUCT_PORTLET_PREFERENCE_NAME;
import static com.ail.pageflow.util.AddProductNameToPageflowContextService.PRODUCT_PORTLET_REQUEST_PARAMETER_NAME;
import static com.ail.pageflow.util.AddProductNameToPageflowContextService.PRODUCT_SESSION_ATTRIBUTE_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.doReturn;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ail.core.BaseException;
import com.ail.core.PreconditionException;

public class AddProductNameToPageflowContextServiceTest {
    AddProductNameToPageflowContextService sut;

    @Mock
    AddProductNameToPageflowContextService.AddProductNameToPageflowContextArgument argument;
    @Mock
    PortletRequest portletRequest;
    @Mock
    PortletPreferences portletPreferences;
    @Mock
    PortletSession portletSession;

    @Before
    public void setupSut() {
        MockitoAnnotations.initMocks(this);
        sut = new AddProductNameToPageflowContextService();
        sut.setArgs(argument);
        doReturn(portletRequest).when(argument).getPortletRequestArg();
        doReturn(portletSession).when(portletRequest).getPortletSession();
        doReturn(portletPreferences).when(portletRequest).getPreferences();
    }

    @Test
    public void checkThatSessionAttributeIsUsed() throws BaseException {
        doReturn("TEST_ATTR").when(portletSession).getAttribute(eq(PRODUCT_SESSION_ATTRIBUTE_NAME));
        doReturn(null).when(portletPreferences).getValue(eq(PRODUCT_PORTLET_PREFERENCE_NAME), (String) isNull());
        doReturn(null).when(portletRequest).getProperty(eq(PRODUCT_PORTLET_REQUEST_PARAMETER_NAME));

        sut.invoke();

        assertEquals("TEST_ATTR", PageflowContext.getProductName());
    }

    @Test
    public void checkThatPortletPreferenceIsUsed() throws BaseException {
        doReturn("TEST_ATTR").when(portletSession).getAttribute(eq(PRODUCT_SESSION_ATTRIBUTE_NAME));
        doReturn("TEST_PREF").when(portletPreferences).getValue(eq(PRODUCT_PORTLET_PREFERENCE_NAME), (String) isNull());
        doReturn(null).when(portletRequest).getProperty(eq(PRODUCT_PORTLET_REQUEST_PARAMETER_NAME));

        sut.invoke();

        assertEquals("TEST_PREF", PageflowContext.getProductName());
    }

    @Test
    public void checkThatRequestParamIsUsed() throws BaseException {
        doReturn("TEST_ATTR").when(portletSession).getAttribute(eq(PRODUCT_SESSION_ATTRIBUTE_NAME));
        doReturn("TEST_PREF").when(portletPreferences).getValue(eq(PRODUCT_PORTLET_PREFERENCE_NAME), (String) isNull());
        doReturn("TEST_PARAM").when(portletRequest).getProperty(eq(PRODUCT_PORTLET_REQUEST_PARAMETER_NAME));

        sut.invoke();

        assertEquals("TEST_PARAM", PageflowContext.getProductName());
    }

    @Test(expected = PreconditionException.class)
    public void checkNullPortletRequestPreconditionsIsChecked() throws BaseException {
        doReturn(null).when(argument).getPortletRequestArg();
        sut.invoke();
    }

    @Test(expected = PreconditionException.class)
    public void checkNullPortletPreferencesPreconditionsIsChecked() throws BaseException {
        doReturn(null).when(portletRequest).getPreferences();
        sut.invoke();
    }

    @Test(expected = PreconditionException.class)
    public void checkNullPortletSessionPreconditionsIsChecked() throws BaseException {
        doReturn(null).when(portletRequest).getPortletSession();
        sut.invoke();
    }

    @Test
    public void checkThatPostconditionsAreChecked() throws BaseException {
        doReturn(null).when(portletSession).getAttribute(eq(PRODUCT_SESSION_ATTRIBUTE_NAME));
        doReturn(null).when(portletPreferences).getValue(eq(PRODUCT_PORTLET_PREFERENCE_NAME), (String) isNull());
        doReturn(null).when(portletRequest).getProperty(eq(PRODUCT_PORTLET_REQUEST_PARAMETER_NAME));

        sut.invoke();

        assertNull(PageflowContext.getProductName());
    }
}