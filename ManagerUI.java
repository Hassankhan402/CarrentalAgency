import java.util.Scanner;

public class ManagerUI implements UserInterface {

    private boolean quit = false;

    @Override
    public void start(Scanner input) {
        while (!quit) {
            displayMenu();
            int selection = getSelection(input);
            execute(selection, input);
        }
    }

    private void execute(int selection, Scanner input) {
        String[] display_lines;

        try {
            switch (selection) {
                
                case 1:
                    display_lines = handleRatesUpdate(input);
                    displayResults(display_lines);
                    break;

               
                case 2:
                    display_lines = SystemInterface.getAllVehicles();
                    displayResults(display_lines);
                    break;

                
                case 3:
                    display_lines = SystemInterface.getAllReservations();
                    displayResults(display_lines);
                    break;

                
                case 4:
                    display_lines = SystemInterface.getAllTransactions();
                    displayResults(display_lines);
                    break;

                
                case 5:
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid selection.");
            }
        } catch (Exception e) {
            System.out.println("Manager error: " + e.getMessage());
        }
    }

    private String[] handleRatesUpdate(Scanner input) {
        System.out.println("\n1 - Display current rates");
        System.out.println("2 - Update car rates");
        System.out.println("3 - Update SUV rates");
        System.out.println("4 - Update minivan rates");
        System.out.print("Select option: ");

        int sel;
        try {
            sel = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            return new String[]{"Invalid selection."};
        }

        switch (sel) {
            case 1:
                return new String[]{
                        "--- Car Rates ---",
                        SystemInterface.getCarRates()[0],
                        "--- SUV Rates ---",
                        SystemInterface.getSUVRates()[0],
                        "--- Minivan Rates ---",
                        SystemInterface.getMinivanRates()[0]
                };
            case 2:
                VehicleRates car = readRates(input, "Car");
                return SystemInterface.updateCarRates(car);
            case 3:
                VehicleRates suv = readRates(input, "SUV");
                return SystemInterface.updateSUVRates(suv);
            case 4:
                VehicleRates van = readRates(input, "Minivan");
                return SystemInterface.updateMinivanRates(van);
            default:
                return new String[]{"Invalid selection."};
        }
    }

    private VehicleRates readRates(Scanner input, String label) {
        System.out.println("Enter new rates for " + label + ":");
        System.out.print("Daily rate: ");
        double d = Double.parseDouble(input.nextLine().trim());
        System.out.print("Weekly rate: ");
        double w = Double.parseDouble(input.nextLine().trim());
        System.out.print("Monthly rate: ");
        double m = Double.parseDouble(input.nextLine().trim());
        System.out.print("Per-mile charge: ");
        double mile = Double.parseDouble(input.nextLine().trim());
        System.out.print("Daily insurance rate: ");
        double ins = Double.parseDouble(input.nextLine().trim());
        return new VehicleRates(d, w, m, mile, ins);
    }

    private void displayMenu() {
        System.out.println("\n--- Manager Menu ---");
        System.out.println("1 - Display/update current rates");
        System.out.println("2 - Display all vehicles");
        System.out.println("3 - Display all reservations");
        System.out.println("4 - Display all transactions");
        System.out.println("5 - Quit to main menu");
    }

    private int getSelection(Scanner input) {
        int sel = -1;
        while (sel < 1 || sel > 5) {
            System.out.print("Enter selection (1–5): ");
            try {
                sel = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                sel = -1;
            }
        }
        return sel;
    }

    private void displayResults(String[] lines) {
        System.out.println();
        for (String s : lines) {
            System.out.println(s);
        }
    }
}

