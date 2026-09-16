package harborlogix.app;

import harborlogix.cargo.*;
import harborlogix.clients.*;
import harborlogix.ops.*;

/**
 * SKELETON - your demonstration program.
 *
 * It must print, in this order, with clear section headings:
 *
 *   1. A yard manifest holding AT LEAST one of every cargo type.
 *   2. Invoices for all three client tiers, showing the discount applied.
 *   3. The drainage round - the one place a downcast is permitted.
 *      Use pattern matching: `if (unit instanceof LiquidTank tank)`.
 *   4. Part D: receive an OversizedCargo into the SAME yard object and
 *      reprint the manifest, proving Yard needed no changes.
 *
 * Build the yard with capacity TariffPolicy.YARD_CAPACITY.
 */
public class TerminalApp {

    public static void main(String[] args) {
        System.out.println("HarborLogix terminal - student " + TariffPolicy.STUDENT_ID);
        System.out.println("==================================================");
        // TODO: sections 1-4
        Client walkInClient = new Client("C-101", "Israel Israeli");
        ContractClient contractClient = new ContractClient("C-102", "LogiTech Logistics", 15.0);
        GovernmentClient govClient = new GovernmentClient("C-103", "Ministry of Defense", "GOV-DEF-01");

        Yard yard = new Yard("Ashdod Main Terminal", TariffPolicy.YARD_CAPACITY);

        StandardContainer stdContainer = new StandardContainer("U-101", walkInClient, 12000.0, 5, 33.0);
        RefrigeratedContainer reeferContainer = new RefrigeratedContainer("U-102", contractClient, 15000.0, 4, 38.0, -18.0, 5.5);
        HazmatContainer hazmatContainer = new HazmatContainer("U-103", govClient, 11000.0, 3, 28.0, 3, true);
        LiquidTank liquidTank = new LiquidTank("U-104", contractClient, 18000.0, 6, 20000.0, 80.0);

        yard.receive(stdContainer);
        yard.receive(reeferContainer);
        yard.receive(hazmatContainer);
        yard.receive(liquidTank);

        System.out.println("\n=== 1. YARD MANIFEST ===");
        yard.printManifest();

        System.out.println("\n=== 2. CLIENT INVOICES ===");
        System.out.printf("Invoice for Walk-in Client (%s, tier: %s, discount: %.1f%%): %.2f NIS%n",
                walkInClient.getName(), walkInClient.clientTier(), walkInClient.discountPercent(), yard.invoiceFor(walkInClient));

        System.out.printf("Invoice for Contract Client (%s, tier: %s, discount: %.1f%%): %.2f NIS%n",
                contractClient.getName(), contractClient.clientTier(), contractClient.discountPercent(), yard.invoiceFor(contractClient));

        System.out.printf("Invoice for Government Client (%s, tier: %s, discount: %.1f%%): %.2f NIS%n",
                govClient.getName(), govClient.clientTier(), govClient.discountPercent(), yard.invoiceFor(govClient));

        System.out.println("\n=== 3. DRAINAGE ROUND (LOWERING LIQUID LEVELS) ===");
        System.out.println("Executing drainage of 3000 litres from liquid tanks...");

        for (CargoUnit unit : yard.getUnits()) {
            if (unit instanceof LiquidTank tank) {
                double litresBefore = tank.currentLitres();
                double drained = tank.transferOut(3000.0);
                System.out.printf("Tank %s: Drained %.1f litres (Before: %.1f L, After: %.1f L)%n",
                        tank.getUnitId(), drained, litresBefore, tank.currentLitres());
            }
        }

        System.out.println("\n--- Yard Manifest After Drainage ---");
        yard.printManifest();

        System.out.println("\n=== 4. PART D: EXTENSION TEST (OVERSIZED CARGO) ===");

        OversizedCargo oversize = new OversizedCargo("U-105", govClient, 25000.0, 2, 14.5, true);

        yard.receive(oversize);
        System.out.println("Oversized cargo successfully received into existing Yard.");
        yard.printManifest();
    }
}
