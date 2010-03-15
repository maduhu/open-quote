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

package com.ail.core;

/**
 * The Precondition exception is thrown to indicate the violation of an entry
 * points precondition.
 */
public class PreconditionException extends BaseException {
	/**
     * Constructor
     * @param description A description of the pre-condition that has been
     * violated.
     */
    public PreconditionException(String description) {
        super(description);
    }

    /**
     * Constructor
     * @param e BaseError causing this failure
     */
    public PreconditionException(BaseError e) {
        super(e);
    }

    /**
     * Constructor
     * @param description A description of the pre-condition that has been
     * violated.
     * @param Exception that caused this failure
     */
    public PreconditionException(String description, Throwable target) {
        super(description, target);
    }
}