public class SystemInterface {

    private static CurrentRates agency_rates;
    private static Vehicles agency_vehicles;
    private static Transactions transactions_history;

    
    public static void initSystem(CurrentRates r, Vehicles v, Transactions t) {
        agency_rates = r;
        agency_vehicles = v;
        transactions_history = t;
    }

    public static boolean initialized() {
        return agency_rates != null;
    }

   

    public static String[] getCarRates() {
        return new String[]{agency_rates.getCarRates().toString()};
    }

    public static String[] getSUVRates() {
        return new String[]{agency_rates.getSUVRates().toString()};
    }

    public static String[] getMinivanRates() {
        return new String[]{agency_rates.getMinivanRates().toString()};
    }

    public static String[] updateCarRates(VehicleRates r) {
        agency_rates.setCarRates(r);
        return new String[]{"Car rates updated: " + r.toString()};
    }

    public static String[] updateSUVRates(VehicleRates r) {
        agency_rates.setSUVRates(r);
        return new String[]{"SUV rates updated: " + r.toString()};
    }

    public static String[] updateMinivanRates(VehicleRates r) {
        agency_rates.setMinivanRates(r);
        return new String[]{"Minivan rates updated: " + r.toString()};
    }

    public static String[] calcRentalCost(RentalDetails details) {
        double cost = agency_rates.calcEstimatedCost(
                details.getVehicleType(),
                details.getRentalPeriod(),
                details.getNumMiles(),
                details.isInsuranceSelected());

        String[] lines = new String[2];
        lines[0] = "Estimated rental cost for: " + details.toString();
        lines[1] = String.format("Total estimated cost: $%.2f", cost);
        return lines;
    }

    public static String[] processReturnedVehicle(String vin, int num_days_used,
                                                  int num_miles_driven, String ccNumber)
            throws VINNotFoundException, UnreservedVehicleException {

        Vehicle v = agency_vehicles.getVehicle(vin);

        if (!v.isReserved()) {
            throw new UnreservedVehicleException("Vehicle " + vin + " is not reserved.");
        }

        ReservationDetails resv = v.getReservation();
        VehicleRates quoted = v.getQuotedRates();

        boolean ins = resv.isInsuranceSelected();

        double cost = agency_rates.calcActualCost(quoted, num_days_used,
                num_miles_driven, ins);

        String type;
        if (v instanceof Car) type = "Car";
        else if (v instanceof SUV) type = "SUV";
        else type = "Minivan";

       
        Transaction tran = new Transaction(
                ccNumber,
                resv.getCustomerName(),
                type,
                resv.getRentalPeriod().toString(),
                num_miles_driven,
                cost);
        transactions_history.add(tran);

        
        try {
            v.cancelReservation();
        } catch (UnreservedVehicleException e) {
            
        }

        String msg = String.format(
                "Total charge for VIN %s for %d days, %d miles = $%.2f",
                vin, num_days_used, num_miles_driven, cost);

        return new String[]{msg};
    }

    

    private static String[] getAvailableByType(Class<?> clazz) {
        agency_vehicles.reset();
        int count = 0;
        while (agency_vehicles.hasNext()) {
            Vehicle v = agency_vehicles.getNext();
            if (clazz.isInstance(v) && !v.isReserved()) count++;
        }

        String[] lines = new String[count == 0 ? 1 : count];
        if (count == 0) {
            lines[0] = "No available vehicles of this type.";
            return lines;
        }

        agency_vehicles.reset();
        int i = 0;
        while (agency_vehicles.hasNext()) {
            Vehicle v = agency_vehicles.getNext();
            if (clazz.isInstance(v) && !v.isReserved()) {
                lines[i++] = v.toString();
            }
        }
        return lines;
    }

    public static String[] getAvailCars() {
        return getAvailableByType(Car.class);
    }

    public static String[] getAvailSUVs() {
        return getAvailableByType(SUV.class);
    }

    public static String[] getAvailMinivans() {
        return getAvailableByType(Minivan.class);
    }

    public static String[] getAllVehicles() {
        agency_vehicles.reset();
        int count = 0;
        while (agency_vehicles.hasNext()) {
            agency_vehicles.getNext();
            count++;
        }

        String[] lines = new String[count];
        agency_vehicles.reset();
        int i = 0;
        while (agency_vehicles.hasNext()) {
            Vehicle v = agency_vehicles.getNext();
            lines[i++] = v.toString();
        }
        return lines;
    }

    public static String[] makeReservation(ReservationDetails details)
            throws VINNotFoundException, ReservedVehicleException {
        Vehicle v = agency_vehicles.getVehicle(details.getVIN());

        v.setReservation(details);

       
        int type;
        if (v instanceof Car) type = 1;
        else if (v instanceof SUV) type = 2;
        else type = 3;
        VehicleRates r = getRatesByType(type);
        v.setQuotedRates(r);

        String msg = "Vehicle " + details.getVIN() +
                " successfully reserved for " + details.getCustomerName();
        return new String[]{msg};
    }

    private static VehicleRates getRatesByType(int vehicleType) {
        if (vehicleType == 1) return agency_rates.getCarRates();
        if (vehicleType == 2) return agency_rates.getSUVRates();
        return agency_rates.getMinivanRates();
    }

    public static String[] cancelReservation(String vin)
            throws VINNotFoundException, UnreservedVehicleException {
        Vehicle v = agency_vehicles.getVehicle(vin);
        v.cancelReservation();
        return new String[]{"Reservation for VIN " + vin + " cancelled."};
    }

    public static String[] getReservation(String vin)
            throws VINNotFoundException {
        Vehicle v = agency_vehicles.getVehicle(vin);
        if (!v.isReserved()) {
            return new String[]{"No reservation exists for VIN " + vin + "."};
        }
        return new String[]{v.getReservation().toString()};
    }

    public static String[] getAllReservations() {
        agency_vehicles.reset();
        int count = 0;
        while (agency_vehicles.hasNext()) {
            Vehicle v = agency_vehicles.getNext();
            if (v.isReserved()) count++;
        }

        if (count == 0) {
            return new String[]{"No reservations on file."};
        }

        String[] lines = new String[count];
        agency_vehicles.reset();
        int i = 0;
        while (agency_vehicles.hasNext()) {
            Vehicle v = agency_vehicles.getNext();
            if (v.isReserved()) {
                lines[i++] = v.getReservation().toString();
            }
        }
        return lines;
    }

   

    public static String[] getAllTransactions() {
        transactions_history.reset();
        if (!transactions_history.hasNext()) {
            return new String[]{"No transactions on file."};
        }

        
        int count = 0;
        while (transactions_history.hasNext()) {
            transactions_history.getNext();
            count++;
        }

        String[] lines = new String[count];
        transactions_history.reset();
        int i = 0;
        while (transactions_history.hasNext()) {
            Transaction t = transactions_history.getNext();
            lines[i++] = t.toString();
        }
        return lines;
    }
}
