package metamas.semanticmodel.protocol;

import java.util.HashMap;
import java.util.Map;
import metamas.utilities.Assert;

/**
 * A protocol.
 * @author Lukáš Kúdela
 * @since 2012-01-12
 * @version %I% %G%
 */
public class Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The name of the interaction protocol. */
    private String name;
    
    private Map<String, Message> messages = new HashMap<String, Message>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Protocol(String name) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        // -------------------------        
        
        this.name = name;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
   
    /**
     * Gets the name.
     * @return The name.
     */
    public String getName() {
        return name;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void addMessage(Message message) {
        // ----- Preconditions -----
        Assert.isNotNull(message, "message");
        // -------------------------
        
        messages.put(message.getName(), message);
    }
    
    // </editor-fold>
}