package jadeorg.proto.organizationprotocol.enactroleprotocol;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.StringMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Role AID' message.
 * A 'Role AID' message is send by an Organization agent to a Player agent
 * as part the 'Enact' protocol and contains information about the Role agent's AID.
 * @author Lukáš Kúdela
 * @since 2011-10-23
 * @version %I% %G%
 */
public class RoleAIDMessage extends StringMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID roleAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public RoleAIDMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public AID getRoleAID() {
        return roleAID;
    }
    
    public RoleAIDMessage setRoleAID(AID roleAID) {
        this.roleAID = roleAID;
        return this;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public String generateContent() {
        return String.format("role-aid(%1$s)", roleAID.getName());
    }

    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("role-aid\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();

        String roleAID = matcher.group(1);
        this.roleAID = new AID(roleAID, true);
    }
    
    // </editor-fold>
}
