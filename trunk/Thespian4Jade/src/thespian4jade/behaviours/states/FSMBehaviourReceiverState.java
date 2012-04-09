package thespian4jade.behaviours.states;

import jade.core.AID;
import thespian4jade.language.Message;

/**
 * An extension of Jade's FSM beahviour that is also a receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-05
 * @version %I% %G%
 */
public abstract class FSMBehaviourReceiverState extends FSMBehaviourState
    implements IReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Receives a message.
     * @param message the message to be received
     * @param senders the senders. More precisely, their AIDs
     * @return <c>true</c> if the message has been received, <c>false</c> otherwise
     */
    @Override
    public boolean receive(Message message, AID... senders) {
        return getParty().receive(message, senders);
    }
    
    // </editor-fold>
}
