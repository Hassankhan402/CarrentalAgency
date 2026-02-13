public class Minivan extends Vehicle {

    public Minivan(String description, int mpg, String vin) {
        super(description, mpg, vin);
    }

    @Override
    public String toString() {
        String base = "Minivan: " + getDescription() + ", MPG: " + getMpg()
                + ", VIN: " + getVIN();
        if (isReserved()) {
            base += " [RESERVED]";
        }
        return base;
    }
}
