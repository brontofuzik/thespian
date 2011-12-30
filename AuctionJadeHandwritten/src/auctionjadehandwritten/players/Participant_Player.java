package auctionjadehandwritten.players;

import auctionjadehandwritten.players.requirements.ComputeFactorial_Requirement;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jadeorg.core.player.Player;
import jadeorg.core.player.PlayerException;
import jadeorg.core.player.kb.RoleDescription;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

/**
 * A Participant player.
 * @author Luk� K�dela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Participant_Player extends Player {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final Set<String> abilities = new HashSet<String>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    static {
        abilities.add("propose-task"); // Auctioneer
        abilities.add("evaluate-task"); // Bidder
        abilities.add("evaluate-bidders"); // Auctioneer
        abilities.add("execute-task"); // Bidder
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    int getTimeout() {
        return new Integer((String)getArguments()[0]).intValue();
    }
    
    List<RoleFullName> getRoles() {
        List<RoleFullName> roles = new LinkedList<RoleFullName>();
        for (int i = 1; i < getArguments().length; i++) {
            roles.add(new RoleFullName((String)getArguments()[i]));
        }
        return roles;
    }
            
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add the requirements.
        addRequirement(new ComputeFactorial_Requirement());
        
        int timeout = getTimeout();
        
        // Add the behaviours.        
        addBehaviour(new EnactRolesWakerBehaviour(this, timeout));
        addBehaviour(new ActivateRoleWakerBehaviour(this, timeout + 1000));
        //addBehaviour(new DeactivateRoleWakerBehaviour(this));
        //addBehaviour(new DeactRolesWakerBehaviour(this));
    }
    
    /**
     * Evaluates a set of requirements.
     * @param requirements the set of requirements to evaluate
     * @return <c>true</c> if all requirements can be met; <c>false</c> otherwise
     */
    @Override
    public boolean evaluateRequirements(String[] requirements) {
        return evaluateAllRequirements(requirements);
    }
  
    // ---------- PRIVATE ----------
    
    // TODO Consider moving this method to the Player superclass.
    /**
     * Evaluates a set of requirements.
     * @param requirements the set of requirements to evaluate
     * @return <c>true</c> if all requirement can be met; <c>false</c> otherwise
     */
    private boolean evaluateAllRequirements(String[] requirements) {
        for (String requirement : requirements) {
            if (evaluateRequirement(requirement)) {
                return false;
            }
        }
        return true;
    }
    
    // TODO Consider moving this method to the Player superclass.
    /**
     * Evaluates a set of requirements.
     * @param requirements the set of requirements to evaluate
     * @return <c>true</c> if any requirement can be met; <c>false</c> otherwise
     */
    private boolean evaluteAnyRequirement(String[] requirements) {
        for (String requirement : requirements) {
            if (evaluateRequirement(requirement)) {
                return true;
            }
        }
        return false;
    }
    
    // TODO Consider moving this method to the Player superclass. 
    /**
     * Evaluates a requirement.
     * @param requirement the requirement to evaluate
     * @return <c>true</c> if all requirements can be met; <c>false</c> otherwise 
     */
    private boolean evaluateRequirement(String requirement) {
        return abilities.contains(requirement);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private static class EnactRolesWakerBehaviour extends WakerBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        EnactRolesWakerBehaviour(Agent agent, int timeout) {
            super(agent, timeout);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
        
        private Participant_Player getMyPlayer() {
            return (Participant_Player)myAgent;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleElapsedTimeout() {
            for (RoleFullName role : getMyPlayer().getRoles()) {            
                try {
                    getMyPlayer().initiateEnactRole(role.getOrganizationName(), role.getRoleName());
                } catch (PlayerException ex) {
                    getMyPlayer().log(Level.SEVERE, String.format("Error: %1$s", ex.getMessage()));
                }
            }
        }
        
        // </editor-fold>
    }
    
    private static class ActivateRoleWakerBehaviour extends WakerBehaviour {
    
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ActivateRoleWakerBehaviour(Agent agent, int timeout) {
            super(agent, timeout);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
        
        private Participant_Player getMyPlayer() {
            return (Participant_Player)myAgent;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleElapsedTimeout() {
            RoleDescription roleToActivate = getMyPlayer().knowledgeBase.getEnactedRoles().iterator().next();
            try {
                getMyPlayer().initiateActivateRole(roleToActivate.getRoleName());
            } catch (PlayerException ex) {
                getMyPlayer().log(Level.SEVERE, String.format("Error: %1$s", ex.getMessage()));
            }
        }
        
        // </editor-fold>
    }
        
    private static class DeactivateRoleWakerBehaviour extends WakerBehaviour {
    
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        DeactivateRoleWakerBehaviour(Agent agent) {
            super(agent, 6000);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
        
        private Participant_Player getMyPlayer() {
            return (Participant_Player)myAgent;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleElapsedTimeout() {            
            RoleDescription roleToDeactivate = getMyPlayer().knowledgeBase.getActiveRole();
            try {
                getMyPlayer().initiateDeactivateRole(roleToDeactivate.getRoleName());
            } catch (PlayerException ex) {
                getMyPlayer().log(Level.SEVERE, String.format("Error: %1$s", ex.getMessage()));
            }
        }
        
        // </editor-fold>
    }
            
    private static class DeactRolesWakerBehaviour extends WakerBehaviour {
    
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        DeactRolesWakerBehaviour(Agent agent) {
            super(agent, 8000);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
        
        private Participant_Player getMyPlayer() {
            return (Participant_Player)myAgent;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleElapsedTimeout() {
            for (RoleDescription roleToDeact : getMyPlayer().knowledgeBase.getEnactedRoles()) {
                try {
                    getMyPlayer().initiateDeactRole(roleToDeact.getOrganizationName(), roleToDeact.getRoleName());
                } catch (PlayerException ex) {
                    getMyPlayer().log(Level.SEVERE, String.format("Error: %1$s", ex.getMessage()));
                }
            }
        }     
        
        // </editor-fold>
    }
    
    // TODO Move this class to the JadeOrg project.
    private static class RoleFullName {
        
        /** The name of organization instance. */
        private String organizationName;
        
        /** The name of roleToDeact class. */
        private String roleName;
        
        RoleFullName(String roleFullName) {
            String[] organizationNameAndRoleName = roleFullName.split("\\.");
            organizationName = organizationNameAndRoleName[0];
            roleName = organizationNameAndRoleName[1];
        }
        
        String getOrganizationName() {
            return organizationName;
        }
        
        String getRoleName() {
            return roleName;
        }
    }
    
    // </editor-fold>
}
