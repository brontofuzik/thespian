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
    
    private BidResult(boolean bidMade, double bid) {
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
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a positive (successful) bid result.
     * Design pattern: Static factory method
     * @param bid the bid
     * @return the positive bid result
     */
    public static BidResult createPositiveBidResult(double bid) {
        return new BidResult(true, bid);
    }
    
    /**
     * Creates a negative (unsuccessful) bid result.
     * Design pattern: Static factory method
     * @return the negative bid result
     */
    public static BidResult createNegativeBidResult() {
        return new BidResult(false, 0.0);
    }
    
    // </editor-fold>
}
