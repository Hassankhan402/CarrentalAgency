public class Car extends Vehicle {

    public Car(String description, int mpg, String vin) {
        super(description, mpg, vin);
    }

    @Override
    public String toString() {
        String base = "Car: " + getDescription() + ", MPG: " + getMpg()
                + ", VIN: " + getVIN();
        if (isReserved()) {
            base += " [RESERVED]";
        }
        return base;
    }
}
