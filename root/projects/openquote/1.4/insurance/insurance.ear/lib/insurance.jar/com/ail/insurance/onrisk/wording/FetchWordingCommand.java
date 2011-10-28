/* Copyright Applied Industrial Logic Limited 2007. All rights Reserved */
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

package com.ail.insurance.onrisk.wording;

import com.ail.core.command.Command;
import com.ail.core.command.CommandArg;
import com.ail.insurance.policy.Policy;

public class FetchWordingCommand extends Command implements FetchWordingArg {
    private static final long serialVersionUID = -4556506348834606687L;
    private FetchWordingArg args;
    
    public FetchWordingCommand() {
        super();
		args=new FetchWordingArgImp();
    }

    public void setArgs(CommandArg arg) {
        this.args=(FetchWordingArg)arg;
    }

    public CommandArg getArgs() {
        return (CommandArg)args;
    }

    /** @inheritDoc */
    public byte[] getDocumentRet() {
        return args.getDocumentRet();
    }
    
    /** @inheritDoc */
    public void setDocumentRet(byte[] documentRet) {
        args.setDocumentRet(documentRet);
    }
    
    /** @inheritDoc */
    public String getPolicyNumberArg() {
        return args.getPolicyNumberArg();
    }
    
    /** @inheritDoc */
    public void setPolicyNumberArg(String quotationNumberArg) {
        args.setPolicyNumberArg(quotationNumberArg);
    }

    public Policy getPolicyRet() {
        return args.getPolicyRet();
    }

    public void setPolicyRet(Policy policyRet) {
        args.setPolicyRet(policyRet);
    }
}