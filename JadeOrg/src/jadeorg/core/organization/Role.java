package jadeorg.core.organization;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;
import jadeorg.core.organization.power.Power;
import java.util.logging.Level;

/**
 * A role agent.
 * @author Lukáš Kúdela
 * @since 2011-10-16
 * @version %I% %G%
 */
public class Role extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    String name;
    
    Organization myOrganization;
    
    Role_InvokePowerResponder invokePowerResponder = new Role_InvokePowerResponder();
    
    RoleState state = RoleState.INACTIVE;
    
    AID playerAID;
    
    // ----- PRIVATE -----
    
    private Logger logger;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Role() {
        logger = jade.util.Logger.getMyLogger(this.getClass().getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Enums">
    
    enum RoleState {
        INACTIVE,
        ACTIVE,
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of this role.
     * @return the name of this role
     */
    public String getRoleName() {
        return name;
    }
    
    /**
     * Sets the name of this role.
     * @param name the name of this role
     */
    public void setRoleName(String name) {
        // ----- Preconditions -----
        assert name != null && !name.isEmpty();
        // -------------------------
        
        this.name = name;
    }
    
    /**
     * Gets my organization
     * @return my organization
     */
    public Organization getMyOrganization() {
        return myOrganization;
    }
    
    /**
     * Sets my organizaiton.
     * @param organization my organization
     */
    public void setMyOrganization(Organization organization) {
        // ----- Preconditions -----
        assert organization != null;
        // -------------------------
        
        this.myOrganization = organization;
    }
    
    public AID getPlayerAID() {
        return playerAID;
    }
    
    public void setPlayerAID(AID playerAID) {
        this.playerAID = playerAID;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void respondToActivateRole(ACLMessage message) {
        logInfo(String.format("Responding to the 'Activate role' protocol (id = %1$s).",
            message.getConversationId()));
        
        if (playerAID.equals(this.playerAID)) {
            // The sender player is enacting this role.
            addBehaviour(new Role_ActivateRoleResponder(message));
        } else {
            // The sender player is not enacting this role.
            // TODO
        }
    }

    public void respondToDeactivateRole(ACLMessage message) {
        logInfo(String.format("Responding to the 'Deactivate role' protocol (id = %1$s).",
            message.getConversationId()));
        
        if (playerAID.equals(this.playerAID)) {
            // The sender player is enacting this role.
            addBehaviour(new Role_DeactivateRoleResponder(message));
        } else {
            // The sender player is not enacting this role.
            // TODO
        }
    }
    
    public void respondToInvokePower(ACLMessage aclMessage) {
        logInfo(String.format("Responding to the 'Invoke power' protocol (id = %1$s).", aclMessage.getConversationId()));
        
        if (playerAID.equals(this.playerAID)) {
            // The sender player is enacting this role.
            invokePowerResponder.setMessage(aclMessage);
            addBehaviour(invokePowerResponder);
        } else {
            // The sender player is not enacting this role.
            // TODO
        }
    }
    
    // ----- Logging -----
    
    /**
     * Logs a message.
     * @param level the level
     * @param message the message
     */
    public void log(Level level, String message) {
        if (logger.isLoggable(level)) {
            logger.log(level, String.format("%1$s: %2$s", getLocalName(), message));
        }
    }
    
    /**
     * Logs an INFO-level message.
     * @param message the INFO-level message
     */
    public void logInfo(String message) {
        log(Level.INFO, message);
    }
    
    // ----- PACKAGE -----
    
    void activate() {
        state = RoleState.ACTIVE;
    }
    
    void deactivate() {
        state = RoleState.INACTIVE;
    }
    
    boolean isActivable() {
        return state == RoleState.INACTIVE;
    }
    
    boolean isDeactivable() {
        return state == RoleState.ACTIVE;
    }
    
    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add behaviours.
        addBehaviour(new Role_Manager());
        logInfo("Behaviours added.");
        
        // TAG YellowPages
        //registerWithYellowPages();
    }
    
    protected void addPower(Power power) {
        invokePowerResponder.addPower(power);
    }
    
    // ----- Yellow pages registration -----
    
    // TAG YellowPages
    private void registerWithYellowPages() { 
        try {
            DFService.register(this, createAgentDescription());
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        logInfo("Registered with the Yellow Pages.");
    }
    
    // TAG YellowPages
    private DFAgentDescription createAgentDescription() {
        // Create the agent description.
        DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(getAID());
        
        // Create the service description.
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("TODO");
        serviceDescription.setName("TODO");
        agentDescription.addServices(serviceDescription);
        
        return agentDescription;
    }
    
    // </editor-fold>
}