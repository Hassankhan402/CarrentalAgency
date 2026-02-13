import java.util.Scanner;

public class EmployeeUI implements UserInterface {

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
        int veh_type;
        String vin, creditcard_num;
        String[] display_lines;
        RentalDetails rental_details;
        ReservationDetails reserv_details;

        try {
            switch (selection) {

                
                case 1:
                    veh_type = getVehicleType(input);
                    switch (veh_type) {
                        case 1:
                            display_lines = SystemInterface.getCarRates();
                            break;
                        case 2:
                            display_lines = SystemInterface.getSUVRates();
                            break;
                        case 3:
                            display_lines = SystemInterface.getMinivanRates();
                            break;
                        default:
                            display_lines = new String[]{"Invalid vehicle type."};
                    }
                    displayResults(display_lines);
                    break;

               
                case 2:
                    veh_type = getVehicleType(input);
                    switch (veh_type) {
                        case 1:
                            display_lines = SystemInterface.getAvailCars();
                            break;
                        case 2:
                            display_lines = SystemInterface.getAvailSUVs();
                            break;
                        case 3:
                            display_lines = SystemInterface.getAvailMinivans();
                            break;
                        default:
                            display_lines = new String[]{"Invalid vehicle type."};
                    }
                    displayResults(display_lines);
                    break;

               
                case 3:
                    rental_details = getRentalDetails(input);
                    display_lines = SystemInterface.calcRentalCost(rental_details);
                    displayResults(display_lines);
                    break;

                
                case 4:
                    reserv_details = getReservationDetails(input);
                    display_lines = SystemInterface.makeReservation(reserv_details);
                    displayResults(display_lines);
                    break;

               
                case 5:
                    vin = getVIN(input);
                    display_lines = SystemInterface.getReservation(vin);
                    displayResults(display_lines);
                    break;

               
                case 6:
                    vin = getVIN(input);
                    display_lines = SystemInterface.cancelReservation(vin);
                    displayResults(display_lines);
                    break;

                
                case 7:
                    creditcard_num = getCreditCardNum(input);
                    vin = getVIN(input);
                    System.out.print("Enter number of days used: ");
                    int num_days_used = Integer.parseInt(input.nextLine().trim());
                    System.out.print("Enter number of miles driven: ");
                    int num_miles_driven = Integer.parseInt(input.nextLine().trim());

                    display_lines = SystemInterface.processReturnedVehicle(vin,
                            num_days_used, num_miles_driven, creditcard_num);
                    displayResults(display_lines);
                    break;

               
                case 8:
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid selection.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayMenu() {
        System.out.println("\n--- Employee Menu ---");
        System.out.println("1 - Display current rates");
        System.out.println("2 - Display available vehicles");
        System.out.println("3 - Display estimated rental cost");
        System.out.println("4 - Make a reservation");
        System.out.println("5 - Display a reservation");
        System.out.println("6 - Cancel a reservation");
        System.out.println("7 - Process returned vehicle");
        System.out.println("8 - Quit to main menu");
    }

    private int getSelection(Scanner input) {
        int sel = -1;
        while (sel < 1 || sel > 8) {
            System.out.print("Enter selection (1-8): ");
            try {
                sel = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                sel = -1;
            }
        }
        return sel;
    }

    private String getVIN(Scanner input) {
        System.out.print("Enter vehicle VIN: ");
        return input.nextLine().trim();
    }

    private String getCreditCardNum(Scanner input) {
        System.out.print("Enter customer credit card number: ");
        return input.nextLine().trim();
    }

    private int getVehicleType(Scanner input) {
        int type = -1;
        while (type < 1 || type > 3) {
            System.out.print("Enter vehicle type (1 = Car, 2 = SUV, 3 = Minivan): ");
            try {
                type = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                type = -1;
            }
        }
        return type;
    }

    private RentalDetails getRentalDetails(Scanner input) {
        int vtype = getVehicleType(input);

        System.out.print("Enter rental period unit (d = days, w = weeks, m = months): ");
        char unit = input.nextLine().trim().toLowerCase().charAt(0);

        System.out.print("Enter quantity for rental period: ");
        int qty = Integer.parseInt(input.nextLine().trim());
        TimePeriod period = new TimePeriod(unit, qty);

        System.out.print("Enter estimated miles to drive: ");
        int miles = Integer.parseInt(input.nextLine().trim());

        System.out.print("Optional insurance? (y/n): ");
        boolean ins = input.nextLine().trim().equalsIgnoreCase("y");

        return new RentalDetails(vtype, period, miles, ins);
    }

    private ReservationDetails getReservationDetails(Scanner input) {
        System.out.print("Enter customer name: ");
        String name = input.nextLine().trim();

        String cc = getCreditCardNum(input);
        String vin = getVIN(input);

        System.out.print("Enter rental period unit (d = days, w = weeks, m = months): ");
        char unit = input.nextLine().trim().toLowerCase().charAt(0);

        System.out.print("Enter quantity for rental period: ");
        int qty = Integer.parseInt(input.nextLine().trim());
        TimePeriod period = new TimePeriod(unit, qty);

        System.out.print("Optional insurance? (y/n): ");
        boolean ins = input.nextLine().trim().equalsIgnoreCase("y");

        return new ReservationDetails(name, cc, period, ins, vin);
    }

    private void displayResults(String[] lines) {
        System.out.println();
        for (String s : lines) {
            System.out.println(s);
        }
    }
}
