package example1.players;

import thespian4jade.concurrency.Future;
import thespian4jade.concurrency.IObservable;
import thespian4jade.concurrency.IObserver;
import thespian4jade.core.Event;
import thespian4jade.core.player.EventHandler;
import thespian4jade.example.CompetencePlayer;

/**
 * Player1 - the player playing the 'Invoker' role.
 * @author Luk� K�dela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class Player1 extends CompetencePlayer implements IObserver {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /** The full name of the 'Invoker' role. */
    private static final String INVOKER_ROLE_FULL_NAME
        = "functionInvocation_Organization.Invoker_Role";
    
    /** The name of the 'Invoke function' competence. */
    private static final String INVOKE_FUNCTION_COMPETENCE_NAME
        = "InvokeFunction_Competence";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Demo1_Player class.
     * Creates the demo1 player who will enact the 'Invoker' role.
     */
    public Player1() {
        super(new RoleFullName(INVOKER_ROLE_FULL_NAME), INVOKE_FUNCTION_COMPETENCE_NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public void update(IObservable observable) {
        // Get the 'Invoke function' competence result.
        Integer invokeFunctionResult = ((Future<Integer>)observable).getValue();
        System.out.println("----- 'Invoke function' competence result: " + invokeFunctionResult + " -----");
        
        deactivateRole();
    }   

    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        super.setup();
        
        // Schedule behaviours.
        // Role enactment
        scheduleEnactRole(2000);
        scheduleSubscribeToEvent(Event.ROLE_ACTIVATED, RoleActivated_EventHandler.class, 3000);
        
        // Competence invocation
        scheduleActivateRole(6000);
        
        // Role deactment
        scheduleDeactRole(10000);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Role activated' event handler.
     * @author Luk� K�dela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class RoleActivated_EventHandler
        extends EventHandler<Player1> {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Handles the 'Role activated' event.
         * @param roleName the name of the activated role
         */
        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Executer_Role")) {
                // Set the 'Invoke function' competence argument. 
                Integer invokeFunctionArgument = new Integer(10);
                System.out.println("----- Invoke function argument: " + invokeFunctionArgument + " -----");
                
                Future<Integer> future = getMyPlayer().invokeCompetence(invokeFunctionArgument);
                future.addObserver(getMyPlayer());
            }
        }
    
        // </editor-fold>    
    }
      
    // </editor-fold>
}
