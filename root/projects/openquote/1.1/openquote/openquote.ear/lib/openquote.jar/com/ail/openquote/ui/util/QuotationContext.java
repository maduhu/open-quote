/* Copyright Applied Industrial Logic Limited 2006. All rights Reserved */
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
package com.ail.openquote.ui.util;

import java.util.Date;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import com.ail.core.CoreProxy;
import com.ail.core.ExceptionRecord;
import com.ail.core.Locale;
import com.ail.core.VersionEffectiveDate;
import com.ail.openquote.Quotation;
import com.ail.openquote.ui.PageFlow;
import com.ail.openquote.ui.RenderingError;

/**
 * This class acts wraps a number of ThreadLocal objects which are initialized at the beginning of each Portal request/response 
 * calls and are available to any code executed within the portal container during the processing of the request/response.
 */
public class QuotationContext {
	private static ThreadLocal<Quotation> quotation = new ThreadLocal<Quotation>() {
		Quotation quotation;
		
		public Quotation get() {
			return quotation;
		}
		
		public void set(Quotation quotation) {
			this.quotation=quotation;
		}
	};
	
	private static ThreadLocal<PageFlow> pageFlow = new ThreadLocal<PageFlow>() {
		PageFlow pageFlow;
		
		public PageFlow get() {
			return pageFlow;
		}
		
		public void set(PageFlow pageFlow) {
			this.pageFlow=pageFlow;
		}
	};
	
	private static ThreadLocal<PortletRequest> request = new ThreadLocal<PortletRequest>() {
		PortletRequest request;
		
		public PortletRequest get() {
			return request;
		}
		
		public void set(PortletRequest request) {
			this.request=request;
		}
	};
	
	private static ThreadLocal<CoreProxy> core = new ThreadLocal<CoreProxy>() {
		CoreProxy core;
		
		public CoreProxy get() {
			return core;
		}
		
		public void set(CoreProxy core) {
			this.core=core;
		}
	};
	
	/**
	 * Initialize the QuotationContext for the current thread. 
	 * @param request Initialise the context with respect to this request
	 */
    public static void initialise(PortletRequest request) {
        Quotation quotation = null;
    	PageFlow pageFlow = null;
    	PortletSession session=request.getPortletSession();
    	PortletPreferences prefs=request.getPreferences();

        CoreProxy core = new CoreProxy();

        try {
            // if there's no quotation in the session, create one.
        	quotation = (Quotation)session.getAttribute("quotation");

        	// get the name of the product we're quoting for. In the sandpit this may be null.
        	String productName=QuotationCommon.productName(session, prefs);
        	
        	if (quotation == null && productName != null) {
                quotation=(Quotation)core.newProductType(productName, "Quotation");
                quotation.setProductTypeId(productName);
            }
    
        	// The request's Locale could change from one request to the next, if the user switches their browser
            // settings for example, so always use the current settings.
        	if (quotation != null) {
        		quotation.setLocale(new Locale(request.getLocale()));
        	}
        	
            // Fetch the appropriate pageflow object for this session. The 'appropriate' pageflow
            // is the one associated with the product we're quoting for in this session. We get
            // the product's name from the quote.
            // If the quote has a quote date, use that as the ved - if it doesn't (as in the case of
            // a new quote), use the date now.
        	if (productName!=null) {
	            Date ved=(quotation.getQuotationDate() != null) ? quotation.getQuotationDate() : new Date();
	            core.setVersionEffectiveDate(new VersionEffectiveDate(ved));
	            pageFlow=(PageFlow)core.newProductType(productName, "QuotationPageFlow");
        	}
        }
        catch(Error e) {
        	if (quotation!=null) {
        		quotation.addException(new ExceptionRecord(e));
        	}
        	else {
        		throw new RenderingError("Failed to initialise quotation instance for product: '"+QuotationCommon.productName(session, prefs)+"'", e);
        	}
        }

        // initialize the thread context
		setRequest(request);
		setQuotation(quotation);
		setCore(core);
		setPageFlow(pageFlow);
    }
    
	/**
	 * Fetch the quotation currently being processed but this thread.
	 * @return current quote, or null if none is defined
	 */
	public static Quotation getQuotation() {
		return quotation.get();
	}
	
	/**
	 * Set the quotation currently being processed.
	 * @param quotationArg
	 */
	public static void setQuotation(Quotation quotationArg) {
		quotation.set(quotationArg);
		request.get().getPortletSession().setAttribute("quotation", quotationArg);
	}
	
	public static CoreProxy getCore() {
		return core.get();
	}
	
	private static void setCore(CoreProxy coreArg) {
		core.set(coreArg);
	}
	
	/**
	 * Fetch the request which is currently being processed.
	 * @return Current request.
	 */
	public static PortletRequest getRequest() {
		return request.get();
	}
	
	/**
	 * Set the portlet request currently being processed.
	 * @param requestArg
	 */
	private static void setRequest(PortletRequest requestArg) {
		request.set(requestArg);
	}

	/**
	 * Fetch the request which is currently being processed.
	 * @return Current request.
	 */
	public static PageFlow getPageFlow() {
		return pageFlow.get();
	}
	
	/**
	 * Set the portlet request currently being processed.
	 * @param requestArg
	 */
	private static void setPageFlow(PageFlow pageFlowArg) {
		pageFlow.set(pageFlowArg);
	}
	
	class ThreadLocalFields {
		
	}
}
