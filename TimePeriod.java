public class TimePeriod {

    private char unit;     
    private int quantity;  

    public TimePeriod(char unit, int quantity) {
        this.unit = unit;
        this.quantity = quantity;
    }

    public char getUnit() {
        return unit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        String u;
        switch (unit) {
            case 'd':
                u = "day(s)";
                break;
            case 'w':
                u = "week(s)";
                break;
            case 'm':
                u = "month(s)";
                break;
            default:
                u = "?";
        }
        return quantity + " " + u;
    }
}
