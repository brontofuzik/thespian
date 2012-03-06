package example1.players.demo;

import thespian4jade.core.player.Player;

/**
 * A Demo player.
 * @author Luk� K�dela
 * @since 2011-11-20
 * @version %I% %G%
 */
public abstract class Demo_Player extends Player {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The full name of the role to enact and activate.
     */
    private RoleFullName roleFullName;   
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates a new Demo player who will enact the Executer role.
     * @param roleFullName the full name of the role to enact and activate
     */
    Demo_Player(RoleFullName roleFullName) {
        // ----- Preconditions -----
        assert roleFullName != null;
        // -------------------------
        
        this.roleFullName = roleFullName;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
     
    @Override
    protected void setup() {
        super.setup();
        
        // Add the responsibilities.
        addRequirement(ExecuteFunction_Responsibility.class);
        
        int timeout = 2000;
        timeout = scheduleEnactRole(roleFullName, timeout);
        timeout = scheduleActivateRole(roleFullName, timeout);
        
        // Schedule individual behaviours.
        timeout = doScheduleBehaviours(timeout);

        timeout = scheduleDeactivateRole(roleFullName, timeout);
        scheduleDeactRole(roleFullName, timeout);
    }
    
    /**
     * Schedule individual behaviours.
     * Design pattern: Template method, Role: Primitive operation
     */
    protected abstract int doScheduleBehaviours(int timeout);

    // </editor-fold>
}