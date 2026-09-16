package harborlogix.clients;

/**
 * SKELETON - implement the TODOs.
 *
 * DESIGN DECISION YOU MUST MAKE AND JUSTIFY IN DESIGN.md:
 * Should Client be abstract, like CargoUnit? Or concrete?
 * Both answers are defensible. Pick one, implement it, and defend it.
 * (The skeleton is concrete; change it if you decide otherwise.)
 */
public class Client {

    // TODO: private final fields for clientId and name
    private final String clientId;
    private final String name;

    public Client(String clientId, String name) {
        // TODO: validate both (non-null, non-blank) and assign
        if (clientId == null || clientId.trim().isEmpty()){
            throw new IllegalArgumentException("clientId cannot be null or empty");
        }

        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("name cannot be null or empty");
        }

        this.clientId = clientId;
        this.name = name;
    }

    public String getClientId() {
        return  this.clientId;
    }

    public String getName() {
        return this.name;
    }

    /** Walk-in clients get no discount. Subclasses may override. */
    public double discountPercent() {
        return 0.0;
    }

    public String clientTier() {
        return "Standard";
    }

    /** Priority clients are unloaded first. */
    public boolean priorityHandling() {
        return false;
    }

    // TODO: override toString()
    @Override
    public String toString() {
        return "Client " + name + " (ID: " + clientId + ")";
    }
}
