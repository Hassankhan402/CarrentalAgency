public class SUV extends Vehicle {

    public SUV(String description, int mpg, String vin) {
        super(description, mpg, vin);
    }

    @Override
    public String toString() {
        String base = "SUV: " + getDescription() + ", MPG: " + getMpg()
                + ", VIN: " + getVIN();
        if (isReserved()) {
            base += " [RESERVED]";
        }
        return base;
    }
}

