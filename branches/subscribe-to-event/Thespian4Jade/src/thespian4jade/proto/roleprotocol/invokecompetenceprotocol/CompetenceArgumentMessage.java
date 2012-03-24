package thespian4jade.proto.roleprotocol.invokecompetenceprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.BinaryMessage;
import thespian4jade.language.IMessageFactory;
import java.io.Serializable;

/**
 * A 'Competence argument' (binary) message.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class CompetenceArgumentMessage<TArgument extends Serializable>
    extends BinaryMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The (serializable) competence argument.
     */
    private TArgument argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the CompetenceArgumentMessage class.
     */
    public CompetenceArgumentMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Gets the competence argument.
     * @return the competence argument
     */
    public TArgument getArgument() {
        return argument;
    }
    
    /**
     * Sets the competence argument
     * @param argument the competence argument
     * @return this 'Competence argument' message (fluent interface)
     */
    public CompetenceArgumentMessage setArgument(TArgument argument) {
        this.argument = argument;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Gets the (serializable) content object.
     * @return the (serializable) content object
     */
    @Override
    protected Serializable getContentObject() {
        return argument;
    }

    /**
     * Sets the (serializable) content object
     * @param contentObject the (serializable) content object
     */
    @Override
    protected void setContentObject(Serializable contentObject) {
        argument = (TArgument)contentObject;
    }
    
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * A 'Competence argument' message factory.
     * @param <TArgument> the competence argument type
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory<TArgument extends Serializable>
        implements IMessageFactory<CompetenceArgumentMessage<TArgument>> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Competence argument' message.
         * @return an empty 'Competence argument' message
         */
        @Override
        public CompetenceArgumentMessage<TArgument> createMessage() {
            return new CompetenceArgumentMessage<TArgument>();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
