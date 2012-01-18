package demo1.organizations;

import demo1.organizations.powers.CalculateFactorial_Power;
import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.Organization;
import jadeorg.core.organization.Role;

/**
 * An English auction organization.
 * @author Luk� K�dela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Demo_Organization extends Organization {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {        
        super.setup();
        
        // Add roles.
        addRole(Asker.class);
        addRole(Answerer.class);
        logInfo("Roles added.");
    }
    
    // </editor-fold>
    
    /**
     * An Asker role.
     * @author Luk� K�dela
     * @since 2011-11-20
     * @version %I% %G%
     */
    public class Asker extends Role {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void setup() {
            super.setup();
            
            // Add powers.
            addPower(CalculateFactorial_Power.class);
            logInfo("Powers added.");
        }
        
        // </editor-fold>
    }
    
    /**
     * An Answerer role.
     * @author Luk� K�dela
     * @since 2011-11-20
     * @version %I% %G%
     */
    public class Answerer extends Role {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Responds to the 'Calculate factorial' protocol.
         * @param protocolId the protocol id
         * @param askerAID the asker AID
         */
        void respondToCalculateFactorial(ACLMessage aclMessage) {

        }
        
        // ----- PROTECTED -----
        
        @Override
        protected void setup() {
            super.setup();
            
            // Add behaviours.
            addBehaviour(new Answerer_Responder());
            logInfo("Behaviours added.");
        }
        
        // </editor-fold>
    }
}
