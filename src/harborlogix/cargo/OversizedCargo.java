package harborlogix.cargo;

import harborlogix.clients.Client;
import harborlogix.ops.TariffPolicy;


public class OversizedCargo extends CargoUnit {

    private final double lengthM;
    private final boolean needsHeavyCrane;

    public OversizedCargo(String unitId, Client owner, double weightKg, int daysStored, double lengthM, boolean needsHeavyCrane) {
        super(unitId, owner, weightKg, daysStored);

        if (lengthM <= 12.0) {
            throw new IllegalArgumentException("Oversized cargo length must be greater than 12.0 metres, got " + lengthM);
        }

        this.lengthM = lengthM;
        this.needsHeavyCrane = needsHeavyCrane;
    }

    public double getLengthM() {
        return lengthM;
    }

    public boolean isNeedsHeavyCrane() {
        return needsHeavyCrane;
    }

    @Override
    public double dailyStorageFee() {
        return TariffPolicy.OVERSIZE_DAILY_FLAT + (lengthM * 5.0);
    }

    @Override
    public String handlingCategory() {
        return "Oversized";
    }

    @Override
    public String safetyBriefing() {
        if (needsHeavyCrane) {
            return "Heavy crane required for lifting; clear wide transport path.";
        }
        return "Ensure wide transport path cleared for oversized unit.";
    }

    @Override
    public String toString() {
        return String.format("%s [Length: %.1fm, Heavy Crane Required: %b]",
                super.toString(), lengthM, needsHeavyCrane);
    }
}