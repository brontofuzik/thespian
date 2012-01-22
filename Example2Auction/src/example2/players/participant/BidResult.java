package example2.players.participant;

/**
 * A 'Bid' requirement result.
 * @author Luk� K�dela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class BidResult {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * A flag indicating wheter a bid has been made.
     */
    private boolean bidMade;
    
    /**
     * The bid.
     */
    private double bid;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public BidResult(boolean bidMade, double bid) {
        // ----- Preconditions -----
        assert !bidMade || bid > 0;
        // -------------------------
        
        this.bidMade = bidMade;
        this.bid = bid;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public boolean isBidMade() {
        return bidMade;
    }

    public double getBid() {
        // ----- Preconditions -----
        if (!bidMade) {
            throw new IllegalStateException("No bid. The bid has not been made.");
        }
        // -------------------------
        return bid;
    }
    
   
    // </editor-fold>
}