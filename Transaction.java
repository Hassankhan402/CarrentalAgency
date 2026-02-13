public class Transaction {

    private String creditcard_num;
    private String customer_name;
    private String vehicle_type;
    private String rental_period;
    private int miles_driven;
    private double rental_cost;

    public Transaction(String creditcard_num, String customer_name,
                       String vehicle_type, String rental_period,
                       int miles_driven, double rental_cost) {
        this.creditcard_num = creditcard_num;
        this.customer_name = customer_name;
        this.vehicle_type = vehicle_type;
        this.rental_period = rental_period;
        this.miles_driven = miles_driven;
        this.rental_cost = rental_cost;
    }

    @Override
    public String toString() {
        return String.format(
                "%s (card #%s), %s, %s, %d miles, $%.2f",
                customer_name, creditcard_num, vehicle_type,
                rental_period, miles_driven, rental_cost);
    }
}

