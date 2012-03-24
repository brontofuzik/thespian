package thespian4jade.protocols.roleprotocol.invokeresponsibilityprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.IMessageFactory;
import thespian4jade.language.SimpleMessage;

/**
 * 
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class ArgumentRequestMessage extends SimpleMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final String CONTENT = "responsibility-argument?";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ArgumentRequestMessage() {
        super(ACLMessage.REQUEST);
        setContent(CONTENT);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * A 'Argument request' message factory.
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory implements IMessageFactory<ArgumentRequestMessage> {

        /**
         * Creates an empty 'Argument request' message.
         * @return an empty 'Argument request' message
         */
        @Override
        public ArgumentRequestMessage createMessage() {
            return new ArgumentRequestMessage();
        }
    }
    
    // </editor-fold>
}