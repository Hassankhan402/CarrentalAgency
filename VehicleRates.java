public class VehicleRates {

    private double daily_rate;
    private double weekly_rate;
    private double monthly_rate;
    private double per_mile_charge;
    private double daily_insur_rate;

    public VehicleRates(double daily_rate, double weekly_rate, double monthly_rate,
                        double per_mile_charge, double daily_insur_rate) {
        this.daily_rate = daily_rate;
        this.weekly_rate = weekly_rate;
        this.monthly_rate = monthly_rate;
        this.per_mile_charge = per_mile_charge;
        this.daily_insur_rate = daily_insur_rate;
    }

   
    public VehicleRates(VehicleRates other) {
        this(other.daily_rate, other.weekly_rate, other.monthly_rate,
                other.per_mile_charge, other.daily_insur_rate);
    }

    public double getDailyRate() {
        return daily_rate;
    }

    public double getWeeklyRate() {
        return weekly_rate;
    }

    public double getMonthlyRate() {
        return monthly_rate;
    }

    public double getMileageChrg() {
        return per_mile_charge;
    }

    public double getDailyInsurRate() {
        return daily_insur_rate;
    }

    @Override
    public String toString() {
        return String.format(
                "Daily: $%.2f  Weekly: $%.2f  Monthly: $%.2f  Per Mile: $%.2f  Daily Insur: $%.2f",
                daily_rate, weekly_rate, monthly_rate, per_mile_charge, daily_insur_rate);
    }
}
