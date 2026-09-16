package harborlogix.clients;

/**
 * SKELETON. A client with a negotiated discount between 0 and 40 percent.
 * Reject anything outside that range with IllegalArgumentException.
 */
public class ContractClient extends Client {

    // TODO: private final field for the negotiated discount
    private final double contractDiscount;

    public ContractClient(String clientId, String name, double contractDiscount) {
        super(clientId, name);
        // TODO: validate 0..40 and assign
        if (contractDiscount < 0 || contractDiscount > 40) {
            throw new IllegalArgumentException("Discount must be between 0 and 40");
        }

        this.contractDiscount = contractDiscount;
    }

    // TODO: override discountPercent(), clientTier(), toString()
    //       toString() must reuse super.toString()

    @Override
    public double discountPercent() {
        return contractDiscount;
    }

    @Override
    public String clientTier(){
        return "Contract";
    }

    @Override
    public String toString(){
        return super.toString() + " [Contract Discount: " + contractDiscount + "%]";
    }
}
