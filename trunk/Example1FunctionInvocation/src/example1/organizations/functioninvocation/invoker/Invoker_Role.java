/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example1.organizations.functioninvocation.invoker;

import jadeorg.core.organization.Role;

/**
 * An Invoker role.
 * @author Luk� K�dela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Invoker_Role extends Role {

    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        // No behaviours.

        // Add powers.
        addPower(InvokeFunction_Competence.class);
        logInfo("Powers added.");
    }

    // </editor-fold>
}
