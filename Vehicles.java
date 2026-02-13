public class Vehicles {

    private Vehicle[] vehicles;
    private int size;
    private int current; 

    public Vehicles(int capacity) {
        vehicles = new Vehicle[capacity];
        size = 0;
        current = 0;
    }

    public void add(Vehicle v) {
        if (size >= vehicles.length) {
            throw new RuntimeException("Vehicle list full");
        }
        vehicles[size++] = v;
    }

    public Vehicle getVehicle(String VIN) throws VINNotFoundException {
        for (int i = 0; i < size; i++) {
            if (vehicles[i].getVIN().equalsIgnoreCase(VIN)) {
                return vehicles[i];
            }
        }
        throw new VINNotFoundException("VIN " + VIN + " not found.");
    }

    // iterator methods
    public void reset() {
        current = 0;
    }

    public boolean hasNext() {
        return current < size;
    }

    public Vehicle getNext() {
        return vehicles[current++];
    }
}
