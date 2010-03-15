/* Copyright Applied Industrial Logic Limited 2003. All rights Reserved */
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

package com.ail.core.product.resetallproducts;

import com.ail.core.command.Command;
import com.ail.core.command.CommandArg;

/**
 * @version $Revision: 1.1 $
 * @state $State: Exp $
 * @date $Date: 2005/10/02 12:15:44 $
 * @source $Source: /home/bob/CVSRepository/projects/core/core.ear/core.jar/com/ail/core/product/resetallproducts/ResetAllProductsCommand.java,v $
 * @stereotype command
 */
public class ResetAllProductsCommand extends Command implements ResetAllProductsArg {
    private ResetAllProductsArg args = null;

    public ResetAllProductsCommand() {
        super();
        args = new ResetAllProductsArgImp();
    }

    public void setArgs(CommandArg arg) {
        this.args = (ResetAllProductsArg)arg;
    }

    public CommandArg getArgs() {
        return args;
    }
}
