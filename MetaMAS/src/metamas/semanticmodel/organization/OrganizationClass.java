package metamas.semanticmodel.organization;

import metamas.utilities.Assert;

/**
 * An organization class.
 * @author Lukáš Kúdela
 * @since 2012-01-10
 * @version %I% %G%
 */
public class OrganizationClass {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public OrganizationClass(String name) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        // -------------------------
        
        this.name = name;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    
    public String getName() {
        return name;
    }
    
    // </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void addRole(RoleClass roleClass) {
    }
    
    public Organization createOrganization(String name) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        // -------------------------
        
        return new Organization(name, this);
    }
    
    // </editor-fold>
}