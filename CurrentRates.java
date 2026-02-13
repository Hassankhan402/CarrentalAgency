public class CurrentRates {

    private VehicleRates[] rates = new VehicleRates[3];  // 0: car, 1: suv, 2: minivan

    public CurrentRates(VehicleRates carRates, VehicleRates suvRates, VehicleRates minRates) {
        rates[0] = carRates;
        rates[1] = suvRates;
        rates[2] = minRates;
    }

    public VehicleRates getCarRates() {
        return rates[0];
    }

    public VehicleRates getSUVRates() {
        return rates[1];
    }

    public VehicleRates getMinivanRates() {
        return rates[2];
    }

    public void setCarRates(VehicleRates r) {
        rates[0] = r;
    }

    public void setSUVRates(VehicleRates r) {
        rates[1] = r;
    }

    public void setMinivanRates(VehicleRates r) {
        rates[2] = r;
    }

    // estimate cost based on CURRENT rates
    public double calcEstimatedCost(int vehicleType, TimePeriod estimatedRentalPeriod,
                                    int estimatedNumMiles, boolean dailyInsur) {

        VehicleRates r = getRatesByType(vehicleType);
        int days = periodToDays(estimatedRentalPeriod);
        return computeTotalCost(r, days, estimatedNumMiles, dailyInsur);
    }

    // actual cost uses QUOTED rates (passed in)
    public double calcActualCost(VehicleRates quotedRates, int num_days_used,
                                 int numMilesDriven, boolean dailyInsur) {
        return computeTotalCost(quotedRates, num_days_used, numMilesDriven, dailyInsur);
    }

    private VehicleRates getRatesByType(int type) {
        if (type == 1) return rates[0];
        if (type == 2) return rates[1];
        return rates[2];
    }

    private int periodToDays(TimePeriod p) {
        switch (p.getUnit()) {
            case 'd':
                return p.getQuantity();
            case 'w':
                return p.getQuantity() * 7;
            case 'm':
                return p.getQuantity() * 31;   
            default:
                return p.getQuantity();
        }
    }

  
    private double computeTotalCost(VehicleRates r, int days,
                                    int miles, boolean dailyInsur) {
        double cost = 0.0;

       
        if (days >= 31) {
            int months = days / 31;
            int rem = days % 31;
            cost += months * r.getMonthlyRate();
            cost += rem * (r.getMonthlyRate() / 31.0);
        } else if (days >= 7) {
            int weeks = days / 7;
            int rem = days % 7;
            cost += weeks * r.getWeeklyRate();
            cost += rem * (r.getWeeklyRate() / 7.0);
        } else {
            cost += days * r.getDailyRate();
        }

        
        cost += miles * r.getMileageChrg();

        
        if (dailyInsur) {
            cost += days * r.getDailyInsurRate();
        }

        return cost;
    }
}
