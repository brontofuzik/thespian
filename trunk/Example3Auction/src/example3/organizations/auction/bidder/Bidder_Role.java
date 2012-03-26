package example3.organizations.auction.bidder;

import thespian4jade.core.organization.Role;

/**
 * A 'Bidder' role.
 * @author Luk� K�dela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Bidder_Role extends Role {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    public static final String NAME = "Bidder_Role";
    
    public static final String BID_RESPONSIBILITY = "Bid_Responsibility";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        addBehaviour(new Bidder_Responder());
        logInfo("Behaviours added.");

        // Add competences.
        // No competences.
    }

    // </editor-fold>
}
