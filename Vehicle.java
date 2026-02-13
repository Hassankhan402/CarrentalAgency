public abstract class Vehicle {

    private String description;      
    private int mpg;                 
    private String vin;             
    private ReservationDetails resv; 
    private VehicleRates quotedRates; 

    public Vehicle(String description, int mpg, String vin) {
        this.description = description;
        this.mpg = mpg;
        this.vin = vin;
        this.resv = null;
        this.quotedRates = null;
    }

    public String getDescription() {
        return description;
    }

    public int getMpg() {
        return mpg;
    }

    public String getVIN() {
        return vin;
    }

    public ReservationDetails getReservation() {
        return resv;
    }

    public VehicleRates getQuotedRates() {
        return quotedRates;
    }

    public boolean isReserved() {
        return resv != null;
    }

    public void setReservation(ReservationDetails details)
            throws ReservedVehicleException {
        if (isReserved()) {
            throw new ReservedVehicleException("Vehicle " + vin + " already reserved.");
        }
        this.resv = details;
    }

    public void setQuotedRates(VehicleRates cost) {
       
        this.quotedRates = new VehicleRates(cost);
    }

    public void cancelReservation() throws UnreservedVehicleException {
        if (!isReserved()) {
            throw new UnreservedVehicleException("Vehicle " + vin + " is not reserved.");
        }
        resv = null;
        quotedRates = null;
    }

    @Override
    public abstract String toString();
}
