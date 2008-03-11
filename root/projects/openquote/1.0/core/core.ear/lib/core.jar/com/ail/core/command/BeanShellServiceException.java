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

package com.ail.core.command;

import com.ail.core.BaseException;
import bsh.EvalError;

/**
 * Any exceptions thrown by BeanShell services are wrapped in the exception.
 * @version $Revision: 1.4 $
 * @state $State: Exp $
 * @date $Date: 2006/01/28 16:54:30 $
 * @source $Source: /home/bob/CVSRepository/projects/core/core.ear/core.jar/com/ail/core/command/BeanShellServiceException.java,v $
 */
public class BeanShellServiceException extends BaseException {
    private EvalError evalError=null;
    
    public BeanShellServiceException(String error) {
        super(error);
    }
    
    public BeanShellServiceException(EvalError evalError) {
        super(evalError.getErrorText()+" at line "+evalError.getErrorLineNumber(), evalError);
        this.evalError=evalError;
    }

    public EvalError getEvalError() {
        return evalError;
    }
}