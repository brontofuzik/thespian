package example1.organizations.functioninvocation;

import example1.organizations.functioninvocation.executer.Executer_Role;
import example1.organizations.functioninvocation.invoker.Invoker_Role;
import jadeorg.core.organization.Organization;

/**
 * An English auction organization.
 * @author Luk� K�dela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class FunctionInvocation_Organization extends Organization {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {        
        super.setup();
        
        // Add roles.
        addRole(Invoker_Role.class);
        addRole(Executer_Role.class);
        logInfo("Roles added.");
    }
    
    // </editor-fold>
}