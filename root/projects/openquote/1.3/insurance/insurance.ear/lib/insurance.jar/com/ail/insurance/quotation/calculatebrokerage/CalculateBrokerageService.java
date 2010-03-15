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

package com.ail.insurance.quotation.calculatebrokerage;

import com.ail.core.BaseException;
import com.ail.core.Core;
import com.ail.core.Functions;
import com.ail.core.PreconditionException;
import com.ail.core.Service;
import com.ail.core.Version;
import com.ail.core.command.CommandArg;
import com.ail.insurance.policy.AssessmentSheet;
import com.ail.insurance.policy.Policy;

/**
 * @version $Revision: 1.3 $
 * @state $State: Exp $
 * @date $Date: 2007/06/10 11:05:59 $
 * @source $Source: /home/bob/CVSRepository/projects/insurance/insurance.ear/insurance.jar/com/ail/insurance/quotation/calculatebrokerage/CalculateBrokerageService.java,v $
 * @stereotype service
 */
public class CalculateBrokerageService extends Service {
    private static final long serialVersionUID = 3821572976058925514L;
    private CalculateBrokerageArg args = null;
    private Core core = null;

    /** Default constructor */
    public CalculateBrokerageService() {
        core = new com.ail.core.Core(this);
    }

    /**
     * Getter to fetch the entry point's code. This method is demanded by the EntryPoint class.
     * @return This entry point's instance of Core.
     */
    public Core getCore() {
        return core;
    }

    /**
     * Fetch the version of this entry point.
     * @return A version object describing the version of this entry point.
     */
    public Version getVersion() {
        com.ail.core.Version v = (com.ail.core.Version) core.newType("Version");
        v.setCopyright("Copyright Applied Industrial Logic Limited 2003. All rights reserved.");
        v.setDate("$Date: 2007/06/10 11:05:59 $");
        v.setSource("$Source: /home/bob/CVSRepository/projects/insurance/insurance.ear/insurance.jar/com/ail/insurance/quotation/calculatebrokerage/CalculateBrokerageService.java,v $");
        v.setState("$State: Exp $");
        v.setVersion("$Revision: 1.3 $");
        return v;
    }

    /**
     * Setter used to the set the entry points arguments.
     * @param args for invoke
     */
    public void setArgs(CommandArg args) {
        this.args = (CalculateBrokerageArg)args;
    }

    /**
     * Getter returning the arguments used by this entry point.
     * @return An instance of CalculateBrokerageArgs.
     */
    public CommandArg getArgs() {
        return args;
    }

    /**
     * Return the product type id of the policy we're assessing the risk for as the
     * configuration namespace. The has the effect of selecting the product's configuration.
     * @return product type id
     */
    public String getConfigurationNamespace() {
        return Functions.productNameToConfigurationNamespace(args.getPolicyArgRet().getProductTypeId());
    }

    /** The 'business logic' of the entry point. */
    public void invoke() throws PreconditionException, BaseException {
        if (args.getPolicyArgRet()==null) {
            throw new PreconditionException("args.getPolicyArgRet()==null");
        }

        Policy policy=args.getPolicyArgRet();

        if (policy.getAssessmentSheet()==null) {
            throw new PreconditionException("policy.getAssessmentSheet()==null");
        }

        AssessmentSheet assessmentSheet=policy.getAssessmentSheet();

        if (policy.getProductTypeId()==null) {
            throw new PreconditionException("policy.getProductTypeId()==null");
        }

        // Remove any lines we added in the past
        assessmentSheet.removeLinesByOrigin("CalculateBrokerage");

        // Lock the assessment sheet
        assessmentSheet.setLockingActor("CalculateBrokerage");

        CalculatePolicyBrokerageCommand command=(CalculatePolicyBrokerageCommand)core.newCommand("CalculatePolicyBrokerage");
        command.setPolicyArg(policy);
        command.setAssessmentSheetArgRet(assessmentSheet);
        command.invoke();
        assessmentSheet=command.getAssessmentSheetArgRet();

        // unlock the assessment sheet
        assessmentSheet.clearLockingActor();

        // put the assessment sheet back into the policy
        policy.setAssessmentSheet(assessmentSheet);

        args.setPolicyArgRet(policy);
    }
}


