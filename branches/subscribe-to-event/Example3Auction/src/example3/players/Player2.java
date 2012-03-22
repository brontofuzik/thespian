package example3.players;

import thespian4jade.core.Event;

/**
 * The 'Participant2' player.
 * @author Luk� K�dela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Player2 extends ParticipantPlayer {  
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">  
    
    /**
     * Initializes the 'Participant2' player.
     */
    public Player2() {
        // Add items to sell.
        addItemToSell(new Item(KOONING, 137.5));
        
        // Add items to buy.
        addItemToBuy(new Item(POLLOCK, 156.8)); // Highest bid.
        addItemToBuy(new Item(KLIMT, 149.2));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        super.setup();
        
        // Add event handlers.
        
        // Add behaviours.
        // Role enactnement
        scheduleEnactRole(getAuctioneerRoleFullName(), 4000);
        scheduleEnactRole(getBidderRoleFullName(), 4000);
        scheduleSubscribeToEvent(getAuctionOrganizationName(), Event.ROLE_ACTIVATED,
            RoleActivated_EventHandler.class, 5000);
        scheduleSubscribeToEvent(getAuctionOrganizationName(), Event.ROLE_DEACTIVATED,
            RoleDeactivated_EventHandler.class, 5000);
        
        // Role activation
        scheduleActivateRole(getAuctioneerRoleFullName(), 10000);
        
        // Role deactment
        scheduleDeactRole(getAuctioneerRoleFullName(), 22000);
        scheduleDeactRole(getBidderRoleFullName(), 23000);
    }
    
    @Override
    protected String getItemToSellName() {
        return KOONING;
    }
    
    // </editor-fold>
}
