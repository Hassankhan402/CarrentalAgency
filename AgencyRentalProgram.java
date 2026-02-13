import java.util.Scanner;

public class AgencyRentalProgram {

    public static void main(String[] args) {

        
        VehicleRates carRates = new VehicleRates(24.95, 169.95, 514.95, 0.16, 14.95);
        VehicleRates suvRates = new VehicleRates(29.95, 189.95, 679.95, 0.16, 14.95);
        VehicleRates minivanRates = new VehicleRates(36.95, 224.95, 687.95, 0.21, 19.95);

        CurrentRates agency_rates =
                new CurrentRates(carRates, suvRates, minivanRates);

      
        Vehicles agency_vehicles = new Vehicles(20);  
        populate(agency_vehicles);

        
        Transactions transactions = new Transactions();

       
        Scanner input = new Scanner(System.in);
        UserInterface ui;
        boolean quit = false;

        while (!quit) { 
            ui = getUI(input);

            if (ui == null) {
                quit = true;
            } else {
               
                if (!SystemInterface.initialized()) {
                    SystemInterface.initSystem(agency_rates, agency_vehicles, transactions);
                }

              
                ui.start(input);
            }
        }

        System.out.println("Exiting program. Goodbye.");
        input.close();
    }

   
    public static UserInterface getUI(Scanner input) {
        boolean valid_selection = false;
        UserInterface ui = null;

        while (!valid_selection) {
            System.out.print("Select interface: 1 – Employee, 2 – Manager, 3 – quit: ");
            int selection;
            try {
                selection = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection – please reenter");
                continue;
            }

            if (selection == 1) {
                ui = new EmployeeUI();
                valid_selection = true;
            } else if (selection == 2) {
                ui = new ManagerUI();
                valid_selection = true;
            } else if (selection == 3) {
                ui = null;
                valid_selection = true;
            } else {
                System.out.println("Invalid selection – please reenter");
            }
        }
        return ui;
    }

   
    private static void populate(Vehicles vehicles) {

     
        vehicles.add(new Car("Toyota Prius", 57, "AED456"));
        vehicles.add(new Car("Honda Insight", 55, "DEF123"));
        vehicles.add(new Car("Hyundai Elantra Hybrid", 53, "JHK857"));

        
        vehicles.add(new SUV("Toyota RAV4 Hybrid", 39, "DPF450"));
        vehicles.add(new SUV("Ford Explorer Hybrid", 31, "WCH302"));
        vehicles.add(new SUV("Honda Pilot Hybrid", 36, "KSB698"));
        vehicles.add(new SUV("Lexus NX 450h+", 37, "GEK334"));

       
        vehicles.add(new Minivan("Toyota Sienna", 36, "AGH890"));
        vehicles.add(new Minivan("Chrysler Pacifica Hybrid", 82, "BFJ386"));
        vehicles.add(new Minivan("Honda Odyssey", 22, "KCM341"));
        vehicles.add(new Minivan("Kia Carnival", 22, "TSH580"));
    }
}
