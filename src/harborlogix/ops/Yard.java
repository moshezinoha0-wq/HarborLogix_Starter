package harborlogix.ops;

import harborlogix.cargo.CargoUnit;
import harborlogix.clients.Client;
import java.util.ArrayList;

/**
 * SKELETON - and the most important file in the assignment.
 *
 * THE RULE
 * --------
 * This file must NOT contain:
 *   - the name of any CargoUnit subclass
 *   - the name of any Client subclass
 *   - the word `instanceof`
 *   - any cast to a hierarchy type
 *
 * Verify with:
 *   java harborlogix.tools.OpenClosedCheck src/harborlogix/ops/Yard.java
 *
 * Yard knows there is *a* cargo unit and *a* client. It knows which questions
 * to ask them. It must not know what kind they are.
 */
public class Yard {

    // TODO: private final String yardName;
    private final String yardName;
    // TODO: private final int capacity;
    private final int capacity;
    // TODO: private final ArrayList<CargoUnit> units = new ArrayList<>();
    private final ArrayList<CargoUnit> units = new  ArrayList<>();

    public Yard(String yardName, int capacity) {
        // TODO: validate and assign
        if (yardName == null || yardName.trim().isEmpty()) {
            throw  new IllegalArgumentException("Yard name cannot be null or empty name");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        this.yardName = yardName;
        this.capacity = capacity;
    }

    public String getYardName() {
        return this.yardName;
    }
    public int getCapacity()    {
        return this.capacity;
    }
    public int getUnitCount()   {
        return this.units.size();
    }

    /**
     * Returns the stored units.
     * THINK: should this return the internal list, or a copy? Your choice
     * affects encapsulation, and you will be asked about it.
     */
    public ArrayList<CargoUnit> getUnits() {
        return new ArrayList<>(this.units);
    }

    /**
     * Adds a unit.
     * Reject null with IllegalArgumentException.
     * Reject a duplicate unit ID with IllegalArgumentException.
     * Reject exceeding capacity with IllegalStateException.
     * Think about why those two situations deserve different exception types.
     */
    public void receive(CargoUnit unit) {
        if (unit == null){
            throw  new IllegalArgumentException("Cargo unit cannot be null");
        }

        for (CargoUnit existing : units) {
            if (existing.getUnitId().equals(unit.getUnitId())) {
                throw new IllegalArgumentException("Duplicate unit ID: " + unit.getUnitId());
            }
        }

        if (units.size() >= this.capacity) {
            throw new IllegalStateException("Yard capacity reached");
        }

        units.add(unit);
    }

    /** Sum of every unit's daily fee. */
    public double totalDailyRevenue() {
        double totalDailyRevenue = 0;
        for (CargoUnit unit : units){
            totalDailyRevenue += unit.dailyStorageFee();
        }
        return totalDailyRevenue;
    }

    /**
     * Total owed by one client: sum of totalStorageCharge() over that client's
     * units, reduced by that client's own discount percentage.
     * Match clients by clientId, not by object identity.
     */
    public double invoiceFor(Client client) {
        if (client == null) {
            return 0.0;
        }

        double totalCharge = 0.0;

        for (CargoUnit unit : units) {
            if (unit.getOwner().getClientId().equals(client.getClientId())) {
                totalCharge += unit.totalStorageCharge();
            }
        }

        double discountPercent = client.discountPercent();
        return totalCharge * (1.0 - discountPercent / 100.0);
    }

    /** The heaviest unit in the yard, or null if the yard is empty. */
    public CargoUnit heaviestUnit() {
        if (units.isEmpty()) {
            return null;
        }

        CargoUnit heaviest =  units.get(0);

        for (CargoUnit unit : units) {
            if (unit.getWeightKg() > heaviest.getWeightKg()) {
                heaviest = unit;
            }
        }
        return heaviest;
    }

    /** Prints one line per unit plus its safety briefing, then the daily revenue. */
    public void printManifest() {
        System.out.println("=== Yard Manifest: " + yardName + " ===");
        for (CargoUnit unit : units) {
            System.out.println(unit);
            System.out.println("  Safety: " + unit.safetyBriefing());
        }
        System.out.printf("Total Daily Storage Revenue: %.2f%n", totalDailyRevenue());
    }
}
