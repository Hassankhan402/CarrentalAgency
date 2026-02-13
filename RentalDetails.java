public class RentalDetails {

   
    private int vehicle_type;
    private TimePeriod rental_period;
    private int num_miles;
    private boolean insurance_selected;

    public RentalDetails(int vehicle_type, TimePeriod rental_period,
                         int num_miles, boolean insurance_selected) {
        this.vehicle_type = vehicle_type;
        this.rental_period = rental_period;
        this.num_miles = num_miles;
        this.insurance_selected = insurance_selected;
    }

    public int getVehicleType() {
        return vehicle_type;
    }

    public TimePeriod getRentalPeriod() {
        return rental_period;
    }

    public int getNumMiles() {
        return num_miles;
    }

    public boolean isInsuranceSelected() {
        return insurance_selected;
    }

    @Override
    public String toString() {
        String typeStr = (vehicle_type == 1 ? "Car"
                : vehicle_type == 2 ? "SUV" : "Minivan");
        return "Type: " + typeStr + ", period: " + rental_period +
                ", miles: " + num_miles +
                ", insurance: " + (insurance_selected ? "yes" : "no");
    }
}
